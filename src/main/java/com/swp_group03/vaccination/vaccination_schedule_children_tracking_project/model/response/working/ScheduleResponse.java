package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO chứa thông tin về lịch làm việc
 * Bao gồm thông tin về tên lịch, ca làm việc và danh sách các ngày làm việc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {
    private String scheduleName;
    private String shiftType;
    private List<WorkDateDTO> workDates;
} 