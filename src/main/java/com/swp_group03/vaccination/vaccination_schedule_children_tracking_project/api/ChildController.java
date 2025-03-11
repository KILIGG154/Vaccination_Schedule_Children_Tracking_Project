package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.ChildrenRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.ChildService;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity createChild(@RequestBody ChildrenRequest child) {
        Child newChild = childService.createChildren(child);
        return ResponseEntity.ok(newChild);
    }

    @PatchMapping("/{child_id}")
    public ResponseEntity updateChild(@Validated @RequestBody ChildrenRequest child, @PathVariable int id) {
        return ResponseEntity.ok(childService.updateChildren(child,id));
    }

    @GetMapping()
    public List<Child>  getChildren(){
        return childService.getChildren();
    }


    @GetMapping("/{name}")
    public ApiResponse<List<ChildResponse>> getChildByName(@PathVariable String name){
        return ApiResponse.<List<ChildResponse>>builder()
                .code(200)
                .result(childService.getChildByName(name))
                .build();
    }
}
