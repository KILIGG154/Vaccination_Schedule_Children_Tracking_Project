package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkDate {
    @Id
    @Column(name = "DateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateId;

    @Column(name = "DayWork", unique = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayWork;

    @Column(name = "ShiftType")
    private String shiftType;
    
    @Column(name = "ScheduleName")
    private String scheduleName;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnore
    private Set<WorkingSchedule> workingSchedules = new HashSet<>();

    public WorkDate() {
    }

    public WorkDate(LocalDate dayWork, String shiftType, String scheduleName) {
        this.dayWork = dayWork;
        this.shiftType = shiftType;
        this.scheduleName = scheduleName;
        this.workingSchedules = new HashSet<>();
    }
}