package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {
    private String scheduleName;
    private String shiftType;
    private Date startDate;
    private Date endDate;
    private boolean repeat;
    private List<String> repeatDays; // ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"]
    


} 