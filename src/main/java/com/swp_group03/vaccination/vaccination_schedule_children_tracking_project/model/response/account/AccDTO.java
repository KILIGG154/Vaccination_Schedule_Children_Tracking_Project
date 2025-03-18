package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccDTO {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private Gender gender; // Change from String to Gender enum

    private boolean status;

    private List<ChildDTO> children;

    public AccDTO (Account account){
       this.firstName = account.getFirstName();
       this.lastName = account.getLastName();
       this.email = account.getEmail();
       this.phoneNumber = account.getPhoneNumber();
       this.address = account.getAddress();
       this.gender = account.getGender();
       this.status = account.isStatus();
       this.children = account.getChild().stream().map(ChildDTO::new).collect(Collectors.toList());
    }
}
