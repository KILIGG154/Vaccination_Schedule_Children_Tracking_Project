package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Working_Schedule")
@IdClass(WorkingScheduleId.class)
public class WorkingSchedule {

    @Id
    @Column(name = "dateId")
    private int dateId;

    @Id
    @Column(name = "AccountId")
    private String accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dateId", insertable = false, updatable = false)
    @JsonIgnore
    private WorkDate schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountId", insertable = false, updatable = false)
    @JsonIgnore
    private Account account;

    @Column(name = "status")
    private boolean status;

    public WorkingSchedule() {
    }

    public WorkingSchedule(int dateId, String accountId, WorkDate schedule, Account account, Boolean status) {
        this.dateId = dateId;
        this.accountId = accountId;
        this.schedule = schedule;
        this.account = account;
        this.status = status;
    }
}