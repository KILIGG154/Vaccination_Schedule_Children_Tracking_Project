package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa thông tin cơ bản về một nhân viên
 * Được sử dụng trong ScheduleDTO để hiển thị danh sách nhân viên trong một lịch làm việc
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffDTO implements StaffInfo {
    private String id;
    private String name;
    private String role;
    private String workStatus; // AVAILABLE, ASSIGNED, BUSY, OFF_DUTY
} 