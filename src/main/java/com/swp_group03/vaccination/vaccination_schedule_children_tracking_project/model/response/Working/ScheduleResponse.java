package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {
    private String scheduleName;
    private String shiftType;
    private List<WorkDate> workDates;

} 