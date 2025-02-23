package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.ChildrenRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.ChildRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    @Autowired
    private ChildRepo childRepo;

    public Child createChildren(ChildrenRequest childrenRequest){
        Child child = new Child();
        child.setChild_name(childrenRequest.getChild_name());
        child.setDob(childrenRequest.getDob());
        child.setHeight(childrenRequest.getHeight());
        child.setWeight(childrenRequest.getWeight());
        child.setGender(childrenRequest.getGender());
        return childRepo.save(child);
    }

    public Child updateChildren(ChildrenRequest child, String id){
        Child chilren = childRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Child not found"));
        if(child.getChild_name() != null) chilren.setChild_name(child.getChild_name());
        if(child.getDob() != null) chilren.setDob(child.getDob());
        if(child.getHeight() != null) chilren.setHeight(child.getHeight());
        if(child.getWeight() != null) chilren.setWeight(child.getWeight());
        if(child.getGender() != null) chilren.setGender(child.getGender());
        return childRepo.save(chilren);
    }
}
