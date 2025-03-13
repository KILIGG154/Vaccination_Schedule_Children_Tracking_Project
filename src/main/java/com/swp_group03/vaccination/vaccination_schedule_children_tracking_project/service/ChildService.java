package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.ChildMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.ChildrenRequest;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.ChildRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChildRepo childRepo;


    @Autowired
    private ChildMapper childMapper;

//    public Child createChildren(ChildrenRequest childrenRequest){
//        Child child = new Child();
//        child.setName(childrenRequest.getName());
//        child.setDob(childrenRequest.getDob());
//        child.setHeight(childrenRequest.getHeight());
//        child.setWeight(childrenRequest.getWeight());
//        child.setGender(childrenRequest.getGender());
//        child.setGender(childrenRequest.getGender());
//        child.setUrlImage(childrenRequest.getUrlImage());
//        child.setAccount_Id(childrenRequest.getAccount_Id());
//        return childRepo.save(child);
//    }
    public Child createChildren(String accountID,ChildrenRequest childrenRequest){
        Account account = userRepo.findById(accountID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        Child child = new Child();
        child.setName(childrenRequest.getName());
        child.setDob(childrenRequest.getDob());
        child.setHeight(childrenRequest.getHeight());
        child.setWeight(childrenRequest.getWeight());
        child.setGender(childrenRequest.getGender());
        child.setGender(childrenRequest.getGender());
        child.setUrlImage(childrenRequest.getUrlImage());
//        child.setBookings(childrenRequest.getBooking());
        child.setAccount(account);
        userRepo.save(account);
        return childRepo.save(child);
    }

    public Child updateChildren(ChildrenRequest child, int id){
        Child chilren = childRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Child not found"));

//        if(child.getChild_name() != null) chilren.setChild_name(child.getChild_name());
//        if(child.getDob() != null) chilren.setDob(child.getDob());
//        if(child.getHeight() != null) chilren.setHeight(child.getHeight());
//        if(child.getWeight() != null) chilren.setWeight(child.getWeight());
//        if(child.getGender() != null) chilren.setGender(child.getGender());
        chilren =  childMapper.toUpdateChild(child);
        return childRepo.save(chilren);
    }

    public List<Child> getChildren(){
        return childRepo.findAll();
    }

    public List<ChildResponse> getChildByName(String name){
        List<Child> lists = childRepo.findByNameContainingIgnoreCase(name);
        return childMapper.toResponseChildren(lists);
    }
//
//    public List<Child> getChildByAccount(String accountID){
//        return childRepo.findBy_AccountId(accountID);
//    }


}
