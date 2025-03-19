package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

public enum BookingStatus {
    PENDING,        // Mới tạo booking
    PAID,        // Đã thanh toán, chờ đến ngày hẹn
    CHECKED_IN,     // Đã check-in tại cơ sở
    ASSIGNED,       // Đã được gán bác sĩ/y tá
    DIAGNOSED,      // Đã được bác sĩ khám và chẩn đoán
    VACCINE_INJECTED, // Đã tiêm vaccine
    COMPLETED,      // Hoàn thành toàn bộ quy trình
    CANCELLED       // Đã hủy
}
