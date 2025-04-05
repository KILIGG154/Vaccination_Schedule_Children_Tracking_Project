package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

//  * Enum định nghĩa các trạng thái làm việc của nhân viên trong lịch làm việc
public enum WorkScheduleStatus {
    AVAILABLE, //     * Nhân viên có thể nhận cuộc hẹn
    ASSIGNED, //     * Nhân viên đã được phân công cho một cuộc hẹn
    OFF_DUTY     // * Nhân viên không thể nhận cuộc hẹn (nghỉ phép, hết ca...)

} 