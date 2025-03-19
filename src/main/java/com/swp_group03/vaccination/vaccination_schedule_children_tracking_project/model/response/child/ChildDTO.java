package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChildDTO {
    private int id;
    private String name;
    private Date dob;
    private String height;
    private String weight;
    private Gender gender;
    private Account account;
//    private String urlImage;

    public ChildDTO(Child child) {
        this.id = child.getId();
        this.name = child.getName();
        this.dob = child.getDob();
        this.height = child.getHeight();
        this.weight = child.getWeight();
        this.gender = child.getGender();
        this.account = child.getAccount();
//        this.urlImage = child.getUrlImage();
    }
}
