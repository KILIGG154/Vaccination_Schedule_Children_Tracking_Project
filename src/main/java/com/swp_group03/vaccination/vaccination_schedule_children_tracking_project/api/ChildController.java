package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.child.ChildrenRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @PostMapping("/{accountID}/create")
    public ResponseEntity createChild(@PathVariable String accountID,@RequestBody ChildrenRequest child) {
        Child newChild = childService.createChildren(accountID,child);
        return ResponseEntity.ok(newChild);
    }

    @PatchMapping("/{child_id}")
    public ResponseEntity updateChild(@Validated @RequestBody ChildrenRequest child, @PathVariable int child_id) {
        return ResponseEntity.ok(childService.updateChildren(child,child_id));
    }

    @GetMapping()
    public List<ChildResponse>  getChildren(){
        return childService.getChildren();
    }


    @GetMapping("/{name}")
    public ApiResponse<List<ChildResponse>> getChildByName(@PathVariable String name){
        return ApiResponse.<List<ChildResponse>>builder()
                .code(200)
                .result(childService.searchChildByName(name))
                .build();
    }

//    @GetMapping("/{accountID}")
//    public ResponseEntity<List<Child>> getChildByAccount(@PathVariable String accountID){
//        List<Child> childList = childService.getChildByAccount(accountID);
//        return ResponseEntity.ok(childList);
//    }

}
