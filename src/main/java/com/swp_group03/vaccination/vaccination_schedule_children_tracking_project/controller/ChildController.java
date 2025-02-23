package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.ChildrenRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.ChildService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @PostMapping("/create")
    public ResponseEntity createChild(@Valid @RequestBody ChildrenRequest child) {
        Child newChild = childService.createChildren(child);
        return ResponseEntity.ok(newChild);
    }

    @PutMapping("/{child_id}")
    public ResponseEntity updateChild(@Valid @RequestBody ChildrenRequest child,  @PathVariable("child_id") String id) {
        return ResponseEntity.ok(childService.updateChildren(child,id));
    }
}
