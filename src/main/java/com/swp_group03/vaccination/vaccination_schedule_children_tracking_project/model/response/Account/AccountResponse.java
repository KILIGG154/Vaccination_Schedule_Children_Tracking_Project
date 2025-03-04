package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AccountResponse {
     String accountId;
     String username;
     String firstName;
     String lastName;
     String email;
     String phoneNumber;
     String address;
     Gender gender;
     boolean status;
     String roleName;

}
