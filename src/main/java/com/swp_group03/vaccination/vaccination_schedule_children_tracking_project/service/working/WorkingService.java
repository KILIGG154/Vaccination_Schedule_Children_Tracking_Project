//package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.working;
//
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.WorkingMapper;
////import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingDetailRequest;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkingResponse;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingDateRepo;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingScheduleRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class WorkingService {
//
//    @Autowired
//    private WorkingDateRepo workingDateRepo;
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private WorkingMapper workingMapper;
//
//    @Autowired
//    private WorkingScheduleRepo workingScheduleRepo;
//
//    /**
//     * Thêm một ngày làm việc mới
//     * @param request Thông tin ngày làm việc
//     * @return Kết quả thêm ngày làm việc
//     */
//    public ApiResponse addWorking(WorkingRequest request) {
//        try {
//            WorkDate work = createWorkDate(request);
//            return ApiResponse.builder().code(201).message("Successfully added working date").build();
//        } catch (Exception e) {
//            return ApiResponse.builder().code(500).message("Error adding working date: " + e.getMessage()).build();
//        }
//    }
//
//    /**
//     * Thêm chi tiết lịch làm việc
////     * @param request Thông tin chi tiết lịch làm việc
//     * @param dateID ID của ngày làm việc
//     * @param accountID ID của tài khoản
//     * @return Kết quả thêm chi tiết lịch làm việc
//     */
//    public ApiResponse addWorkingDetail(int dateID, String accountID) {
//        try {
//            createWorkingSchedule(dateID, accountID);
//            return ApiResponse.builder().code(201).message("Successfully added working schedule detail").build();
//        } catch (Exception e) {
//            return ApiResponse.builder().code(500).message("Error adding working schedule detail: " + e.getMessage()).build();
//        }
//    }
//
////    public ApiResponse<List<WorkingResponse>> getAllWorking(String accountID) {
////        try {
////            account account = userRepo.findByAccountId(accountID);
////            if (account == null) {
////                return ApiResponse.<List<WorkingResponse>>builder()
////                        .code(404)
////                        .message("account not found")
////                        .build();
////            }
////
////            List<WorkingSchedule> schedules = workingScheduleRepo.findByAccountId(accountID);
////            List<WorkingResponse> workingSchedules = schedules.stream()
////                    .map(schedule -> new WorkingResponse(
////                            schedule.getDateId(),
////                            schedule.getAccountId(),
////                            schedule.getSchedule(),
////                            schedule.isStatus() ? "Active" : "Inactive"
////                    ))
////                    .collect(Collectors.toList());
////
////            return ApiResponse.<List<WorkingResponse>>builder()
////                    .code(200)
////                    .message("Successfully retrieved working schedules")
////                    .result(workingSchedules)
////                    .build();
////        } catch (Exception e) {
////            return ApiResponse.<List<WorkingResponse>>builder()
////                    .code(500)
////                    .message("Error retrieving working schedules: " + e.getMessage())
////                    .build();
////        }
////    }
//    /**
//     * Lấy danh sách lịch làm việc của một tài khoản
//     * @param accountID ID của tài khoản
//     * @return Danh sách lịch làm việc
//     */
//    @Transactional(readOnly = true)
//    public ApiResponse<List<WorkingResponse>> getAllWorking(String accountID) {
//        try {
//            Account account = userRepo.findByAccountId(accountID);
//            if (account == null) {
//                return ApiResponse.<List<WorkingResponse>>builder()
//                        .code(404)
//                        .message("account not found")
//                        .build();
//            }
//
//            List<WorkingResponse> workingSchedules = workingMapper.toGetAllWorking(workingScheduleRepo.findByAccountId(accountID));
//
//            return ApiResponse.<List<WorkingResponse>>builder()
//                    .code(200)
//                    .message("Successfully retrieved working schedules")
//                    .result(workingSchedules)
//                    .build();
//        } catch (Exception e) {
//            return ApiResponse.<List<WorkingResponse>>builder()
//                    .code(500)
//                    .message("Error retrieving working schedules: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    public WorkDate createWorkDate(WorkingRequest request){
//       WorkDate workDate = new WorkDate();
//         workDate.setDayWork(request.getDayWork());
//         workDate.setShiftType(request.getShiftType());
////         workDate.setEndTime(request.getEndTime());
//         return workingDateRepo.save(workDate);
//    }
//
//    private WorkingSchedule createWorkingSchedule(int workDateId, String accountId){
//        WorkDate workDate = workingDateRepo.findById(workDateId).orElseThrow(() -> new RuntimeException("WorkDate not found with id: " ));
//        Account account = userRepo.findById(accountId).orElseThrow(() -> new RuntimeException("account not found with id: " ));
//
////        WorkingScheduleId work = new WorkingScheduleId();
////        work.setDateId(request.getDateID());
////        work.setAccountId(request.getAccountID());
//
//        WorkingSchedule workingSchedule = new WorkingSchedule();
//        workingSchedule.setDateId(workDateId);
//        workingSchedule.setAccountId(accountId);
//        workingSchedule.setSchedule(workDate);
//        workingSchedule.setAccount(account);
//        workingSchedule.setStatus(true);
//
//        return workingScheduleRepo.save(workingSchedule);
//    }
//
//    public List<WorkingResponse> getAllWorkingResponseList(String accountID , int dateID){
//
//    Account account = userRepo.findByAccountId(accountID);
//
//    WorkDate workDate = workingDateRepo.findById(dateID).orElseThrow(() -> new RuntimeException("WorkDate not found with id: " ));
//        List<WorkingResponse> workingSchedules = workingMapper.toGetAllWorking(workingScheduleRepo.findAll());
//
//        return workingSchedules;
//
//
//    }
//
//
//}
