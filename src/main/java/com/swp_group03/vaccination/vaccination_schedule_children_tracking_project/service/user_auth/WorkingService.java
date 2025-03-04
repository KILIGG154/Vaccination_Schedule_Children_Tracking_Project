package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.user_auth;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingScheduleId;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.WorkingMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingDateRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.WorkingScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkingService {

    @Autowired
    private WorkingDateRepo workingDateRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WorkingMapper workingMapper;

    @Autowired
    private WorkingScheduleRepo workingScheduleRepo;

    public WorkDate createWorkDate(WorkingRequest request){
        WorkDate workDate = workingMapper.createWorkDate(request);
        return workingDateRepo.save(workDate);
    }

    public WorkingSchedule createWorkingSchedule(WorkingDetailRequest request, int workDateId, String accountId){
        WorkDate workDate = workingDateRepo.findById(workDateId).orElseThrow(() -> new RuntimeException("WorkDate not found with id: " ));
        Account account = userRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found with id: " ));

        WorkingScheduleId work = new WorkingScheduleId();
        work.setDateId(request.getDateID());
        work.setAccountId(request.getAccountID());

        WorkingSchedule workingSchedule = new WorkingSchedule();
        workingSchedule.setDateId(workDateId);
        workingSchedule.setAccountId(accountId);
        workingSchedule.setSchedule(workDate);
        workingSchedule.setAccount(account);
        workingSchedule.setStatus(true);

        return workingScheduleRepo.save(workingSchedule);
    }
}
