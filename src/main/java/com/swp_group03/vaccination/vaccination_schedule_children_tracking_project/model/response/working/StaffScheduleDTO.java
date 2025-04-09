package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO chứa thông tin về lịch làm việc của một nhân viên
 * Bao gồm thông tin cơ bản về nhân viên và danh sách các ngày làm việc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffScheduleDTO implements StaffInfo {
    private String id;
    private String name;
    private List<WorkDateDTO> schedules;
} 