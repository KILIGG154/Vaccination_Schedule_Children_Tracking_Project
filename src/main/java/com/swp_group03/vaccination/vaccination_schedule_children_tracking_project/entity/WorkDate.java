package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Work_Date")
public class WorkDate {
    @Id
    @Column(name = "ScheduleId", nullable = false)
    private int id;

    @Column(name = "Day_Work")
    private LocalDate dayWork;

    @Column(name = "Start_time")
    private String startTime;

    @Column(name = "End_time")
    private String endTime;

    @OneToMany(mappedBy = "schedule")
    private Set<WorkingSchedule> workingSchedules = new HashSet<>();

}