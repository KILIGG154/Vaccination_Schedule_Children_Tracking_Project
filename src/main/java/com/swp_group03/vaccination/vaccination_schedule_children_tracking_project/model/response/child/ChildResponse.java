package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildResponse {
    private String name;
    private Date dob;
    private String height;
    private String weight;
    private Gender gender;
    private String urlImage;
    private Account account_Id;
}
