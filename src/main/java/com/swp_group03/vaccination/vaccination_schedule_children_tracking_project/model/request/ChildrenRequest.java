package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenRequest {
    @NotBlank(message = "Vui lòng nhập tên cảu trẻ")
    private String child_name;
   // @NotBlank(message = "Vui lòng nhập ngày tháng năm sinh của trẻ")
    private Date dob;
    @NotBlank(message = "Vui lòng nhập chiều cao của trẻ")
    private String height;
    @NotBlank(message = "Vui lòng nhập cân nặng của trẻ")
    private String weight;
    @NotBlank(message = "Vui lòng chọn giới tính của trẻ")
    private String gender;

}
