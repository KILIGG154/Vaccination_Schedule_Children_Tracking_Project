package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.ChildMapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.child.ChildrenRequest;
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
    public Child createChildren(String accountID, ChildrenRequest childrenRequest){
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

    public Child updateChildren(ChildrenRequest request, int childId){
        Child child = childRepo.findById(childId).orElseThrow(() -> new EntityNotFoundException("Child not found"));

        if(request.getName() != null) child.setName(request.getName());
        if(child.getDob() != null) child.setDob(request.getDob());
        if(child.getHeight() != null) child.setHeight(request.getHeight());
        if(child.getWeight() != null) child.setWeight(request.getWeight());
        if(child.getGender() != null) child.setGender(request.getGender());
        if(child.getUrlImage() != null) child.setUrlImage(request.getUrlImage());
        return childRepo.save(child);
    }

    public List<ChildResponse> getChildren(){
        return childMapper.toResponseChildren(childRepo.findAll());
    }

    public List<ChildResponse> searchChildByName(String name){
        List<Child> lists = childRepo.findByNameContainingIgnoreCase(name);
        return childMapper.toResponseChildren(lists);
    }
//
//    public List<Child> getChildByAccount(String accountID){
//        return childRepo.findBy_AccountId(accountID);
//    }


}
