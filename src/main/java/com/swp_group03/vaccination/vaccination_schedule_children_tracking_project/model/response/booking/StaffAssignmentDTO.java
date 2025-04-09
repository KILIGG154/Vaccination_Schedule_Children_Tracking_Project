package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO cho việc gán nhân viên vào booking
 * Sử dụng trong phương thức assignStaffToBooking của BookingService
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffAssignmentDTO {
    private String staffId;
    private String staffName;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;
    
    private String shiftType;
    private String role;
} 