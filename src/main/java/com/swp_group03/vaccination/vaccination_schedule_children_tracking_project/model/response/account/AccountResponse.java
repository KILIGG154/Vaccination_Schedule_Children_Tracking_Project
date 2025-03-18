package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
