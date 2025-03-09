package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Work_Date")
public class WorkDate {
    @Id
    @Column(name = "DateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DayWork")
    private Date dayWork;

    @Column(name = "StartTime")
    private String startTime;

    @Column(name = "EndTime")
    private String endTime;

    @OneToMany(mappedBy = "schedule")
    private Set<WorkingSchedule> workingSchedules = new HashSet<>();

    public WorkDate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDayWork() {
        return dayWork;
    }

    public void setDayWork(Date dayWork) {
        this.dayWork = dayWork;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Set<WorkingSchedule> getWorkingSchedules() {
        return workingSchedules;
    }

    public void setWorkingSchedules(Set<WorkingSchedule> workingSchedules) {
        this.workingSchedules = workingSchedules;
    }
}