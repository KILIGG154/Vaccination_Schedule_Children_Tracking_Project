package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ScheduleDTO {
    private int id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    
    private String dayOfWeek;
    private String shiftType;
    private String scheduleName;
    private List<StaffDTO> staff;
} 