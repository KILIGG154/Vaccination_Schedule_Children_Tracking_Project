package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.StaffScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkDateDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.working.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/working")
public class WorkingController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * API tạo lịch làm việc với danh sách nhân viên
     */
    @PostMapping("/api/schedules")
    public ApiResponse<Map<String, Integer>> createScheduleWithStaff(@RequestBody ScheduleRequest request) {
        return scheduleService.createScheduleWithStaff(request);
    }

    /**
     * API thêm nhân viên vào lịch làm việc hiện có
     */
    @PostMapping("/api/schedules/add-staff")
    public ApiResponse<Map<String, Integer>> addStaffToExistingSchedule(@RequestBody StaffScheduleRequest request) {
        return scheduleService.addStaffToExistingSchedule(request);
    }

    /**
     * API lấy danh sách lịch làm việc trong khoảng thời gian
     */
    @GetMapping("/api/schedules")
    public ApiResponse<List<ScheduleDTO>> getSchedules(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return scheduleService.getSchedules(startDate, endDate);
    }

    /**
     * API lấy danh sách ngày làm việc có sẵn trong khoảng thời gian
     */
    @GetMapping("/api/working-dates/available")
    public ApiResponse<List<WorkDateDTO>> getAvailableWorkingDates(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return scheduleService.getAvailableWorkingDates(startDate, endDate);
    }

    /**
     * API lấy lịch làm việc của một nhân viên trong khoảng thời gian
     */
    @GetMapping("/api/staff/{staffId}/schedule")
    public ApiResponse<StaffScheduleDTO> getStaffSchedule(
            @PathVariable String staffId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return scheduleService.getStaffSchedule(staffId, startDate, endDate);
    }

    /**
     * API lấy tất cả các ngày làm việc (WorkDate) hiện có trong hệ thống
     */
    @GetMapping("/api/working-dates/all")
    public ApiResponse<List<WorkDateDTO>> getAllWorkingDates() {
        return scheduleService.getAllWorkingDates();
    }
}