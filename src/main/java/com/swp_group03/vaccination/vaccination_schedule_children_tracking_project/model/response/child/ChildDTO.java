package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccountBasicInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildDTO {
    private int id;
    private String name;
    private Date dob;
    private String height;
    private String weight;
    private Gender gender;
    private String urlImage;
    private AccountBasicInfo account;

    public ChildDTO(Child child) {
        this.id = child.getId();
        this.name = child.getName();
        this.dob = child.getDob();
        this.height = child.getHeight();
        this.weight = child.getWeight();
        this.gender = child.getGender();
        this.urlImage = child.getUrlImage();
        
        if (child.getAccount() != null) {
            this.account = new AccountBasicInfo(child.getAccount());
        }
    }
}
