package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingRequest {


    private Date dayWork;

    private String startTime;

    private String endTime;


    public WorkingRequest() {
    }

    public WorkingRequest(int id, Date dayWork, String startTime, String endTime) {
        this.dayWork = dayWork;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
