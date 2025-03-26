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

    @Column(name = "dateId", insertable = true, updatable = true)
    private int dateId;

    @Column(name = "AccountId", insertable = true, updatable = true)
    private String accountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dateId", referencedColumnName = "DateId", insertable = false, updatable = false)
    @JsonIgnore
    private WorkDate schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AccountId", referencedColumnName = "accountId", insertable = false, updatable = false)
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
        this.status = status != null ? status : true;
    }
}