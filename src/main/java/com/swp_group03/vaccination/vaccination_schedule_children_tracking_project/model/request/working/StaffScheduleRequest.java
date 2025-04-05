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
public class StaffScheduleRequest {
    private String staffId; // ID của nhân viên được chỉ định (used for single staff assignment)
    private Date startDate;
    private Date endDate;
    private boolean repeatPattern;
    private List<Integer> weekdays; // [1, 2, 3, 4, 5, 6, 7] where 1 = Monday, ..., 7 = Sunday
    private List<String> staffIds; // IDs of staff to be assigned (used for batch assignment)
} 