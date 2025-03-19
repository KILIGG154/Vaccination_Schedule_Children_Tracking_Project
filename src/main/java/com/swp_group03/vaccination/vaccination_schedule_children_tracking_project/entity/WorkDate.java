package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkDate {
    @Id
    @Column(name = "DateId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DayWork", unique = true)
    @Temporal(TemporalType.DATE) // Chỉ lưu ngày, không có giờ
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dayWork;

    @Column(name = "ShiftType")
    private String shiftType;

//    @Column(name = "EndTime")
//    private String endTime;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnore
    private Set<WorkingSchedule> workingSchedules = new HashSet<>();

    public WorkDate() {
    }

    public WorkDate(Date dayWork, String shiftType, String endTime, Set<WorkingSchedule> workingSchedules) {
        this.dayWork = dayWork;
        this.shiftType = shiftType;
//        this.endTime = endTime;
        this.workingSchedules = workingSchedules;
    }
}