package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

/**
 * Interface chung cho các DTO liên quan đến thông tin nhân viên
 * Được sử dụng bởi StaffDTO và StaffScheduleDTO
 */
public interface StaffInfo {
    String getId();
    String getName();
    void setId(String id);
    void setName(String name);
} 