package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.WorkingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.WorkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/working")
public class WorkingController {

    @Autowired
    private WorkingService workingService;

    @PostMapping("add")
    public ApiResponse addWorking(@RequestBody WorkingRequest request) {
        workingService.createWorkDate(request);
        return ApiResponse.builder().code(201).message("Successfully added working date").build();
    }

    @PostMapping("/detail/{dateID}/{accountID}")
    public ApiResponse addWorkingDetail(WorkingDetailRequest request, @PathVariable int dateID, @PathVariable String accountID) {
        workingService.createWorkingSchedule(request, dateID, accountID);
        return ApiResponse.builder().code(201).message("Successfully added working schedule detail").build();
    }
}
