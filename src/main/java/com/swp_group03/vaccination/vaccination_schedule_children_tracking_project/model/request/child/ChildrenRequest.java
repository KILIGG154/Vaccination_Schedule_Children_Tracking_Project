package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.child;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ChildrenRequest {
//    @NotBlank(message = "Vui lòng nhập tên cảu trẻ")
//     String child_name;
//   // @NotBlank(message = "Vui lòng nhập ngày tháng năm sinh của trẻ")
//     Date dob;
//    @NotBlank(message = "Vui lòng nhập chiều cao của trẻ")
//     String height;
//    @NotBlank(message = "Vui lòng nhập cân nặng của trẻ")
//     String weight;
//    @NotBlank(message = "Vui lòng chọn giới tính của trẻ")
//     String gender;


    private String name;
    private Date dob;
    private String height;
    private String weight;
    private Gender gender;
    private String urlImage;
    private Booking booking;


}
