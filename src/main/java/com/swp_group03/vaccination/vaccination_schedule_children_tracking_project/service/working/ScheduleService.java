package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.working;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkScheduleStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.WorkScheduleMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.StaffScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkDateDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingDateRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private WorkingDateRepo workDateRepository;

    @Autowired
    private WorkingScheduleRepo workingScheduleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WorkScheduleMapper workScheduleMapper;

    /**
     * Tạo lịch làm việc mới với danh sách nhân viên được chỉ định
     * @param request Thông tin lịch làm việc và danh sách ID nhân viên
     * @return ApiResponse chứa thông tin về số lượng ngày làm việc và số lượng phân công đã được tạo
     */
    @Transactional
    public ApiResponse<Map<String, Integer>> createScheduleWithStaff(ScheduleRequest request) {
        try {
            // Validate request
        if (request.getStartDate() == null || request.getEndDate() == null ||
            request.getStartDate().after(request.getEndDate())) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(400)
                        .message("Ngày bắt đầu và ngày kết thúc không hợp lệ")
                        .build();
            }
            
            if (request.getStaffIds() == null || request.getStaffIds().isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(400)
                        .message("Danh sách nhân viên không được để trống")
                        .build();
            }
            
            // Tạo các ngày làm việc từ request
            List<WorkDate> workDates = workScheduleMapper.createWorkDatesFromRequest(request);
            
            // Lưu các ngày làm việc vào database
            Map<Date, WorkDate> savedWorkDates = saveWorkDates(workDates);
            
            // Lấy danh sách nhân viên
            List<Account> staffList = userRepo.findAllById(request.getStaffIds());
            if (staffList.isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(404)
                        .message("Không tìm thấy nhân viên nào với ID được cung cấp")
                        .build();
            }
            
            // Tạo và lưu các phân công làm việc
            int assignmentsCount = assignStaffToWorkDates(savedWorkDates.values(), staffList);
            
            // Trả về kết quả
            Map<String, Integer> result = new HashMap<>();
            result.put("workingDatesCount", savedWorkDates.size());
            result.put("assignmentsCount", assignmentsCount);
            
            return ApiResponse.<Map<String, Integer>>builder()
                    .code(201)
                    .message("Đã tạo lịch làm việc thành công")
                    .result(result)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.<Map<String, Integer>>builder()
                    .code(500)
                    .message("Lỗi khi tạo lịch làm việc: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Thêm nhân viên vào các ngày làm việc hiện có
     * @param request Thông tin về khoảng thời gian, mẫu lặp lại và danh sách nhân viên
     * @return ApiResponse chứa thông tin về số lượng phân công đã được tạo
     */
    @Transactional
    public ApiResponse<Map<String, Integer>> addStaffToExistingSchedule(StaffScheduleRequest request) {
        try {
            // Validate request
            if (request.getStartDate() == null || request.getEndDate() == null || 
                request.getStartDate().after(request.getEndDate())) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(400)
                        .message("Ngày bắt đầu và ngày kết thúc không hợp lệ")
                        .build();
            }
            
            if (request.getStaffIds() == null || request.getStaffIds().isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(400)
                        .message("Danh sách nhân viên không được để trống")
                        .build();
            }
            
            // Tìm các ngày làm việc hiện có trong khoảng thời gian
            List<WorkDate> existingWorkDates = workDateRepository.findByDayWorkBetween(
                    request.getStartDate(), request.getEndDate());
            
            if (existingWorkDates.isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(404)
                        .message("Không tìm thấy ngày làm việc nào trong khoảng thời gian đã chọn")
                        .build();
            }
            
            // Lọc theo mẫu lặp lại nếu có
            if (request.isRepeatPattern() && request.getWeekdays() != null && !request.getWeekdays().isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                existingWorkDates = existingWorkDates.stream()
                        .filter(workDate -> {
                            calendar.setTime(workDate.getDayWork());
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            // Convert from Calendar.DAY_OF_WEEK (Sunday = 1) to our format (Monday = 1)
                            int ourDayOfWeek = dayOfWeek == Calendar.SUNDAY ? 7 : dayOfWeek - 1;
                            return request.getWeekdays().contains(ourDayOfWeek);
                        })
                        .collect(Collectors.toList());
            }
            
            if (existingWorkDates.isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(404)
                        .message("Không tìm thấy ngày làm việc nào phù hợp với mẫu lặp lại đã chọn")
                        .build();
            }
            
            // Lấy danh sách nhân viên
            List<Account> staffList = userRepo.findAllById(request.getStaffIds());
            if (staffList.isEmpty()) {
                return ApiResponse.<Map<String, Integer>>builder()
                        .code(404)
                        .message("Không tìm thấy nhân viên nào với ID được cung cấp")
                        .build();
            }
            
            // Lấy các phân công hiện có để tránh trùng lặp
            Set<String> existingAssignments = getExistingAssignments(existingWorkDates, staffList);
            
            // Tạo và lưu các phân công làm việc mới
            int assignmentsCount = assignStaffToWorkDatesNoDuplicates(existingWorkDates, staffList, existingAssignments);
            
            // Trả về kết quả
            Map<String, Integer> result = new HashMap<>();
            result.put("addedCount", assignmentsCount);
            
            return ApiResponse.<Map<String, Integer>>builder()
                    .code(201)
                    .message("Đã thêm " + assignmentsCount + " phân công mới")
                    .result(result)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Map<String, Integer>>builder()
                    .code(500)
                    .message("Lỗi khi thêm nhân viên vào lịch: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Lấy danh sách lịch làm việc trong khoảng thời gian
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return ApiResponse chứa danh sách lịch làm việc
     */
    public ApiResponse<List<ScheduleDTO>> getSchedules(Date startDate, Date endDate) {
        try {
            // Tìm các ngày làm việc trong khoảng thời gian
            List<WorkDate> workDates = workDateRepository.findByDayWorkBetween(startDate, endDate);
            
            if (workDates.isEmpty()) {
                return ApiResponse.<List<ScheduleDTO>>builder()
                        .code(404)
                        .message("Không tìm thấy lịch làm việc nào trong khoảng thời gian đã chọn")
                        .build();
            }
            
            // Lấy tất cả các phân công làm việc của các ngày này
            List<Integer> dateIds = workDates.stream().map(WorkDate::getDateId).collect(Collectors.toList());
            List<WorkingSchedule> workingSchedules = new ArrayList<>();
            
            // Lấy từng batch để tránh query quá lớn
            int batchSize = 100;
            for (int i = 0; i < dateIds.size(); i += batchSize) {
                int end = Math.min(i + batchSize, dateIds.size());
                List<Integer> batch = dateIds.subList(i, end);
                
                for (Integer dateId : batch) {
                    workingSchedules.addAll(workingScheduleRepo.findByDateId(dateId));
                }
            }
            
            // Lấy danh sách tất cả các account IDs từ workingSchedules
            Set<String> accountIds = workingSchedules.stream()
                    .map(WorkingSchedule::getAccountId)
                    .collect(Collectors.toSet());
            
            // Lấy thông tin account
            List<Account> accounts = userRepo.findAllById(accountIds);
            Map<String, Account> accountMap = accounts.stream()
                    .collect(Collectors.toMap(Account::getAccountId, account -> account));
            
            // Chuyển đổi dữ liệu sang DTO và trả về
            List<ScheduleDTO> scheduleDTOs = workDates.stream()
                    .map(workDate -> workScheduleMapper.toScheduleDTO(workDate, workingSchedules, accountMap))
                    .collect(Collectors.toList());
            
            return ApiResponse.<List<ScheduleDTO>>builder()
                    .code(200)
                    .message("Lấy danh sách lịch làm việc thành công")
                    .result(scheduleDTOs)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<ScheduleDTO>>builder()
                    .code(500)
                    .message("Lỗi khi lấy danh sách lịch làm việc: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Lấy danh sách ngày làm việc có sẵn trong khoảng thời gian
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return ApiResponse chứa danh sách ngày làm việc
     */
    public ApiResponse<List<WorkDateDTO>> getAvailableWorkingDates(Date startDate, Date endDate) {
        try {
            // Tìm các ngày làm việc trong khoảng thời gian
            List<WorkDate> workDates = workDateRepository.findByDayWorkBetween(startDate, endDate);
            
            if (workDates.isEmpty()) {
                return ApiResponse.<List<WorkDateDTO>>builder()
                        .code(404)
                        .message("Không tìm thấy ngày làm việc nào trong khoảng thời gian đã chọn")
                        .build();
            }
            
            // Chuyển đổi dữ liệu sang DTO sử dụng MapStruct
            List<WorkDateDTO> workDateDTOs = workDates.stream()
                    .map(workDate -> workScheduleMapper.toWorkDateDTO(workDate))
                    .collect(Collectors.toList());
            
            return ApiResponse.<List<WorkDateDTO>>builder()
                    .code(200)
                    .message("Lấy danh sách ngày làm việc thành công")
                    .result(workDateDTOs)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<WorkDateDTO>>builder()
                    .code(500)
                    .message("Lỗi khi lấy danh sách ngày làm việc: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Lấy lịch làm việc của một nhân viên trong khoảng thời gian
     * @param staffId ID của nhân viên
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return ApiResponse chứa lịch làm việc của nhân viên
     */
    public ApiResponse<StaffScheduleDTO> getStaffSchedule(String staffId, Date startDate, Date endDate) {
        try {
            // Kiểm tra nhân viên tồn tại
            Account staff = userRepo.findById(staffId).orElse(null);
            if (staff == null) {
                return ApiResponse.<StaffScheduleDTO>builder()
                        .code(404)
                        .message("Không tìm thấy nhân viên với ID: " + staffId)
                        .build();
            }
            
            // Tìm các ngày làm việc trong khoảng thời gian
            List<WorkDate> workDates = workDateRepository.findByDayWorkBetween(startDate, endDate);
            if (workDates.isEmpty()) {
                return ApiResponse.<StaffScheduleDTO>builder()
                        .code(404)
                        .message("Không tìm thấy ngày làm việc nào trong khoảng thời gian đã chọn")
                        .build();
            }
            
            // Lấy tất cả các phân công làm việc của nhân viên này
            List<WorkingSchedule> staffSchedules = workingScheduleRepo.findByAccountId(staffId);
            
            // Lọc các phân công làm việc theo ngày
            Set<Integer> workDateIds = workDates.stream().map(WorkDate::getDateId).collect(Collectors.toSet());
            List<WorkingSchedule> filteredSchedules = staffSchedules.stream()
                    .filter(schedule -> workDateIds.contains(schedule.getDateId()))
                    .collect(Collectors.toList());
            
            if (filteredSchedules.isEmpty()) {
                return ApiResponse.<StaffScheduleDTO>builder()
                        .code(404)
                        .message("Không tìm thấy lịch làm việc nào cho nhân viên này trong khoảng thời gian đã chọn")
                        .build();
            }
            
            // Chuyển đổi dữ liệu sang DTO
            StaffScheduleDTO dto = workScheduleMapper.toStaffScheduleDTO(staff, filteredSchedules);
            
            return ApiResponse.<StaffScheduleDTO>builder()
                    .code(200)
                    .message("Lấy lịch làm việc của nhân viên thành công")
                    .result(dto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<StaffScheduleDTO>builder()
                    .code(500)
                    .message("Lỗi khi lấy lịch làm việc của nhân viên: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Update the work schedule status to AVAILABLE for today's schedules
     * This method should be called by a scheduled task at the beginning of each day
     */
    @Transactional
    public void updateTodaySchedulesToAvailable() {
        // Lấy ngày hiện tại (chỉ phần ngày, không có giờ)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        
        // Tìm WorkDate cho ngày hôm nay
        Optional<WorkDate> todayWorkDate = workDateRepository.findByDayWork(today);
        
        if (todayWorkDate.isPresent()) {
            // Tìm tất cả lịch làm việc cho ngày hôm nay
            List<WorkingSchedule> todaySchedules = workingScheduleRepo.findByDateId(todayWorkDate.get().getDateId());
            
            // Cập nhật trạng thái của tất cả lịch làm việc thành AVAILABLE
            for (WorkingSchedule schedule : todaySchedules) {
                // Chỉ cập nhật trạng thái nếu hiện tại là OFF_DUTY và status là true (active)
                if (schedule.getWorkStatus() == WorkScheduleStatus.OFF_DUTY && schedule.isStatus()) {
                    schedule.setWorkStatus(WorkScheduleStatus.AVAILABLE);
                }
            }
            
            // Lưu các thay đổi
            workingScheduleRepo.saveAll(todaySchedules);
        }
    }

    /**
     * Lấy tất cả các ngày làm việc (WorkDate) hiện có trong hệ thống
     * @return ApiResponse chứa danh sách tất cả các ngày làm việc
     */
    public ApiResponse<List<WorkDateDTO>> getAllWorkingDates() {
        try {
            // Lấy tất cả các ngày làm việc từ repository
            List<WorkDate> allWorkDates = workDateRepository.findAll();
            
            if (allWorkDates.isEmpty()) {
                return ApiResponse.<List<WorkDateDTO>>builder()
                        .code(404)
                        .message("Không tìm thấy ngày làm việc nào trong hệ thống")
                        .build();
            }
            
            // Sắp xếp ngày làm việc theo thứ tự tăng dần
            allWorkDates.sort(Comparator.comparing(WorkDate::getDayWork));
            
            // Chuyển đổi dữ liệu sang DTO
            List<WorkDateDTO> workDateDTOs = allWorkDates.stream()
                    .map(workDate -> workScheduleMapper.toWorkDateDTO(workDate))
                    .collect(Collectors.toList());
            
            return ApiResponse.<List<WorkDateDTO>>builder()
                    .code(200)
                    .message("Lấy danh sách tất cả ngày làm việc thành công")
                    .result(workDateDTOs)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<WorkDateDTO>>builder()
                    .code(500)
                    .message("Lỗi khi lấy danh sách ngày làm việc: " + e.getMessage())
                    .build();
        }
    }

    // Helper methods

    /**
     * Lưu các ngày làm việc vào database, không lưu trùng lặp
     * @param workDates Danh sách ngày làm việc cần lưu
     * @return Map chứa các ngày làm việc đã lưu với key là ngày làm việc
     */
    private Map<Date, WorkDate> saveWorkDates(List<WorkDate> workDates) {
        Map<Date, WorkDate> savedWorkDates = new HashMap<>();
        
        for (WorkDate workDate : workDates) {
            // Kiểm tra xem ngày làm việc này đã tồn tại chưa
            Optional<WorkDate> existingWorkDate = workDateRepository.findByDayWork(workDate.getDayWork());
            
            if (existingWorkDate.isPresent()) {
                // Nếu đã tồn tại, sử dụng ngày làm việc hiện có
                savedWorkDates.put(workDate.getDayWork(), existingWorkDate.get());
            } else {
                // Nếu chưa tồn tại, lưu ngày làm việc mới
                WorkDate saved = workDateRepository.save(workDate);
                savedWorkDates.put(saved.getDayWork(), saved);
            }
        }
        
        return savedWorkDates;
    }

    /**
     * Assign nhân viên vào các ngày làm việc
     * @param workDates Danh sách ngày làm việc
     * @param staffList Danh sách nhân viên
     * @return Số lượng phân công đã được tạo
     */
    private int assignStaffToWorkDates(Collection<WorkDate> workDates, List<Account> staffList) {
        int count = 0;
        
        for (WorkDate workDate : workDates) {
            for (Account staff : staffList) {
                // Tạo phân công làm việc mới
                WorkingSchedule schedule = new WorkingSchedule();
                schedule.setSchedule(workDate);
                schedule.setAccount(staff);
                schedule.setStatus(true);
                schedule.setWorkStatus(WorkScheduleStatus.AVAILABLE);
                
                // Lưu phân công làm việc vào database
                workingScheduleRepo.save(schedule);
                count++;
            }
        }
        
        return count;
    }

    /**
     * Lấy các phân công hiện có
     * @param workDates Danh sách ngày làm việc
     * @param staffList Danh sách nhân viên
     * @return Set chứa các cặp (dateId, accountId) đã tồn tại
     */
    private Set<String> getExistingAssignments(List<WorkDate> workDates, List<Account> staffList) {
        Set<Integer> dateIds = workDates.stream().map(WorkDate::getDateId).collect(Collectors.toSet());
        Set<String> staffIds = staffList.stream().map(Account::getAccountId).collect(Collectors.toSet());
        
        // Lấy tất cả các phân công hiện có cho các ngày và nhân viên đã chọn
        List<WorkingSchedule> existingSchedules = workingScheduleRepo.findAll().stream()
                .filter(schedule -> dateIds.contains(schedule.getDateId()) && staffIds.contains(schedule.getAccountId()))
                .collect(Collectors.toList());
        
        // Tạo các cặp dateId-accountId để kiểm tra trùng lặp
        return existingSchedules.stream()
                .map(schedule -> schedule.getDateId() + "-" + schedule.getAccountId())
                .collect(Collectors.toSet());
    }

    /**
     * Assign nhân viên vào các ngày làm việc, tránh trùng lặp
     * @param workDates Danh sách ngày làm việc
     * @param staffList Danh sách nhân viên
     * @param existingAssignments Set chứa các phân công đã tồn tại
     * @return Số lượng phân công đã được tạo
     */
    private int assignStaffToWorkDatesNoDuplicates(List<WorkDate> workDates, List<Account> staffList, Set<String> existingAssignments) {
        int count = 0;
        
        for (WorkDate workDate : workDates) {
            int dateId = workDate.getDateId();
            
            for (Account staff : staffList) {
                String staffId = staff.getAccountId();
                String key = dateId + "-" + staffId;
                
                // Kiểm tra xem phân công này đã tồn tại chưa
                if (!existingAssignments.contains(key)) {
                    // Tạo phân công làm việc mới
                    WorkingSchedule schedule = new WorkingSchedule();
                    schedule.setSchedule(workDate);
                    schedule.setAccount(staff);
                    schedule.setStatus(true);
                    schedule.setWorkStatus(WorkScheduleStatus.AVAILABLE);
                    
                    // Lưu phân công làm việc vào database
                    workingScheduleRepo.save(schedule);
                    count++;
                }
            }
        }
        
        return count;
    }
}