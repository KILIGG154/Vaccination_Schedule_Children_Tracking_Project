package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;


public class WorkingRequest {


    private int id;

    private LocalDate dayWork;

    private LocalTime startTime;

    private LocalTime endTime;


    public WorkingRequest() {
    }

    public WorkingRequest(int id, LocalDate dayWork, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.dayWork = dayWork;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDayWork() {
        return dayWork;
    }

    public void setDayWork(LocalDate dayWork) {
        this.dayWork = dayWork;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
