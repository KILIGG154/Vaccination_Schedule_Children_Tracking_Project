package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.child.ChildDTO;
//import jakarta.persistence.Column;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccDTO {
    private String accountId;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String address;
    private List<ChildDTO> children;

    public AccDTO(Account account) {
        this.accountId = account.getAccountId();
        this.username = account.getUsername();
        this.lastName = account.getLastName();
        this.firstName = account.getFirstName();
        this.email = account.getEmail();
        this.phoneNumber = account.getPhoneNumber();
        this.gender = account.getGender();
        this.address = account.getAddress();
        
        // Map each Child to a ChildDTO that includes the correct ID field
        if (account.getChild() != null) {
            this.children = account.getChild().stream()
                .map(ChildDTO::new)
                .collect(Collectors.toList());
        } else {
            this.children = new ArrayList<>();
        }
    }
}
