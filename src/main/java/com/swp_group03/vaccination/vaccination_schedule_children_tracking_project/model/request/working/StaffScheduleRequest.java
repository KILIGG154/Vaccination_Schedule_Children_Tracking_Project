package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffScheduleRequest {
    private LocalDate startDate; // Ngày bắt đầu khoảng thời gian tìm kiếm ngày làm việc
    private LocalDate endDate;   // Ngày kết thúc khoảng thời gian tìm kiếm ngày làm việc
    private boolean repeatPattern; // Có áp dụng mẫu lặp lại không (ví dụ: chỉ thêm vào các ngày thứ 2,4,6)
    private List<Integer> weekdays; // [1, 2, 3, 4, 5, 6, 7] where 1 = Monday, ..., 7 = Sunday
    private List<String> staffIds; // Danh sách ID nhân viên cần thêm vào các ngày làm việc
} 