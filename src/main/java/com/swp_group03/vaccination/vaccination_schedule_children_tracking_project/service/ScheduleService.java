package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.WorkingMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.ScheduleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingDateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private WorkingDateRepo workDateRepository;

    @Autowired
    private WorkingMapper workingMapper;

    /**
     * Tạo lịch làm việc dựa trên thông tin từ request
     * @param request Thông tin lịch làm việc
     * @return Danh sách các ngày làm việc đã được tạo
     */
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        List<WorkDate> workDates = new ArrayList<>();
        
        // Kiểm tra nếu ngày bắt đầu và ngày kết thúc hợp lệ
        if (request.getStartDate() == null || request.getEndDate() == null || 
            request.getStartDate().after(request.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không hợp lệ");
        }
        
        // Nếu có lặp lại và khoảng cách giữa ngày bắt đầu và kết thúc > 7 ngày
        if (request.isRepeat() && daysBetween(request.getStartDate(), request.getEndDate()) > 7) {
            workDates = createRepeatingSchedule(request);
        } else {
            // Nếu không lặp lại, tạo lịch làm việc từ ngày bắt đầu đến ngày kết thúc
            workDates = createNonRepeatingSchedule(request);
        }
        
        // Lưu danh sách các ngày làm việc vào database
        workDates = workDateRepository.saveAll(workDates);
        
        // Trả về response
        return workingMapper.toScheduleResponse(request.getScheduleName(), request.getShiftType(), workDates);
    }
    
    /**
     * Tạo lịch làm việc không lặp lại
     * @param request Thông tin lịch làm việc
     * @return Danh sách các ngày làm việc
     */
    private List<WorkDate> createNonRepeatingSchedule(ScheduleRequest request) {
        List<WorkDate> workDates = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getStartDate());
        
        // Tạo lịch làm việc từ ngày bắt đầu đến ngày kết thúc
        while (!calendar.getTime().after(request.getEndDate())) {
            WorkDate workDate = workingMapper.scheduleRequestToWorkDate(request);
            workDate.setDayWork(calendar.getTime());
            workDates.add(workDate);
            
            // Tăng ngày lên 1
            calendar.add(Calendar.DATE, 1);
        }
        
        return workDates;
    }
    
    /**
     * Tạo lịch làm việc lặp lại theo các ngày trong tuần
     * @param request Thông tin lịch làm việc
     * @return Danh sách các ngày làm việc
     */
    private List<WorkDate> createRepeatingSchedule(ScheduleRequest request) {
        List<WorkDate> workDates = new ArrayList<>();
        
        // Danh sách các ngày trong tuần (0 = Chủ nhật, 1 = Thứ 2, ..., 6 = Thứ 7)
        List<Integer> daysOfWeek = convertRepeatDaysToDayOfWeek(request.getRepeatDays());
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getStartDate());
        
        // Tạo lịch làm việc từ ngày bắt đầu đến ngày kết thúc
        while (!calendar.getTime().after(request.getEndDate())) {
            // Kiểm tra xem ngày hiện tại có thuộc danh sách các ngày lặp lại không
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            
            if (daysOfWeek.contains(dayOfWeek)) {
                WorkDate workDate = workingMapper.scheduleRequestToWorkDate(request);
                workDate.setDayWork(calendar.getTime());
                workDates.add(workDate);
            }
            
            // Tăng ngày lên 1
            calendar.add(Calendar.DATE, 1);
        }
        
        return workDates;
    }
    
    /**
     * Chuyển đổi danh sách các ngày lặp lại từ chuỗi sang số ngày trong tuần
     * @param repeatDays Danh sách các ngày lặp lại
     * @return Danh sách các số ngày trong tuần
     */
    private List<Integer> convertRepeatDaysToDayOfWeek(List<String> repeatDays) {
        List<Integer> daysOfWeek = new ArrayList<>();
        
        if (repeatDays == null || repeatDays.isEmpty()) {
            return daysOfWeek;
        }
        
        for (String day : repeatDays) {
            switch (day.toUpperCase()) {
                case "MON":
                    daysOfWeek.add(Calendar.MONDAY);
                    break;
                case "TUE":
                    daysOfWeek.add(Calendar.TUESDAY);
                    break;
                case "WED":
                    daysOfWeek.add(Calendar.WEDNESDAY);
                    break;
                case "THU":
                    daysOfWeek.add(Calendar.THURSDAY);
                    break;
                case "FRI":
                    daysOfWeek.add(Calendar.FRIDAY);
                    break;
                case "SAT":
                    daysOfWeek.add(Calendar.SATURDAY);
                    break;
                case "SUN":
                    daysOfWeek.add(Calendar.SUNDAY);
                    break;
            }
        }
        
        return daysOfWeek;
    }
    
    /**
     * Tính số ngày giữa hai ngày
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return Số ngày giữa hai ngày
     */
    private long daysBetween(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }
} 