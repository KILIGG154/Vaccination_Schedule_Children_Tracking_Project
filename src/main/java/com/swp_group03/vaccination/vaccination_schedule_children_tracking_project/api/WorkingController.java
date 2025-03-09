package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccountResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.WorkingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.UserService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth.WorkingService;
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

    @PostMapping("add")
    public ApiResponse addWorking(@RequestBody WorkingRequest request) {
        WorkDate work = workingService.createWorkDate(request);
        return ApiResponse.builder().code(201).message("Successfully added working date").build();
    }

    @PostMapping("/detail/{dateID}/{accountID}")
    public ApiResponse addWorkingDetail(WorkingDetailRequest request, @PathVariable int dateID, @PathVariable String accountID) {
         workingService.createWorkingSchedule(request, dateID, accountID);
        return ApiResponse.builder().code(201).message("Successfully added working schedule detail").build();
    }

    @GetMapping("/all/{accountID}/{dateID}")
    public ApiResponse<List<WorkingResponse>> getAllWorking(@PathVariable String accountID) {
        // có một hàm tìm và trả về response của account
        ApiResponse<AccountResponse> account = new ApiResponse<>();
        account.setResult(userService.getAccountById(accountID));
        // có 1 hàm tìm và trả về response của date


        // trả chung giữa 2 thằng
        ApiResponse<List<WorkingResponse>> lists =  new ApiResponse<>();
//        lists.setResult(workingService.getAllWorkingResponseList());
        lists.setCode(300);
        return lists;
    }

}
