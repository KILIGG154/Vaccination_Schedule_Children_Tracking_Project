package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Working_Schedule")
public class WorkingSchedule {

    @Id
    @Column(name = "scheduleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dateId", referencedColumnName = "DateId")
    @JsonIgnore
    private WorkDate schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AccountId", referencedColumnName = "accountId")
    @JsonIgnore
    private Account account;

    @Column(name = "status")
    private boolean status; // true = active, false = inactive

    @Column(name = "workStatus")
    @Enumerated(EnumType.STRING)
    private WorkScheduleStatus workStatus; // Using enum instead of String

    public WorkingSchedule() {
        this.status = true;
        this.workStatus = WorkScheduleStatus.OFF_DUTY;
    }

    public WorkingSchedule(WorkDate schedule, Account account, Boolean status) {
        this.schedule = schedule;
        this.account = account;
        this.status = status != null ? status : true;
        this.workStatus = WorkScheduleStatus.OFF_DUTY;
    }
    
    /**
     * Phương thức tiện ích để thiết lập dateId thông qua WorkDate
     */
    public void setDateId(int dateId) {
        if (this.schedule == null) {
            this.schedule = new WorkDate();
        }
        this.schedule.setDateId(dateId);
    }
    
    /**
     * Phương thức tiện ích để thiết lập accountId thông qua Account
     */
    public void setAccountId(String accountId) {
        if (this.account == null) {
            this.account = new Account();
        }
        this.account.setAccountId(accountId);
    }
    
    /**
     * Phương thức tiện ích để lấy dateId từ WorkDate
     */
    public int getDateId() {
        return this.schedule != null ? this.schedule.getDateId() : 0;
    }
    
    /**
     * Phương thức tiện ích để lấy accountId từ Account
     */
    public String getAccountId() {
        return this.account != null ? this.account.getAccountId() : null;
    }
}