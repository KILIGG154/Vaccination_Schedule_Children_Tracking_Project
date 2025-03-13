package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.ScheduleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.WorkingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.UserService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.WorkingService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working")
public class WorkingController {

    @Autowired
    private WorkingService workingService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("add")
    public ApiResponse addWorking(@RequestBody WorkingRequest request) {
        return workingService.addWorking(request);
    }

    @PostMapping("/detail/{dateID}/{accountID}")
//    public ApiResponse addWorkingDetail(@RequestBody WorkingDetailRequest request, @PathVariable int dateID, @PathVariable String accountID)
    public ApiResponse addWorkingDetail(@PathVariable int dateID, @PathVariable String accountID)
    {
        return workingService.addWorkingDetail(dateID, accountID);
    }

    @GetMapping("/allworkdate/{accountID}")
    public ApiResponse<List<WorkingResponse>> getAllWorkDateByAccountID(@PathVariable String accountID) {
        return workingService.getAllWorking(accountID);
    }
    
    /**
     * Tạo lịch làm việc mới
     * @param request Thông tin lịch làm việc
     * @return Danh sách các ngày làm việc đã được tạo
     */

    @PostMapping("/schedule/create")
    public ApiResponse<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        try {
            ScheduleResponse response = scheduleService.createSchedule(request);
            return ApiResponse.<ScheduleResponse>builder()
                    .code(200)
                    .message("Tạo lịch làm việc thành công")
                    .result(response)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ScheduleResponse>builder()
                    .code(500)
                    .message("Lỗi khi tạo lịch làm việc: " + e.getMessage())
                    .build();
        }
    }
}
