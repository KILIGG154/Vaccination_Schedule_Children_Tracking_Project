package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Gender;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountUpdate {

     String firstName; // Changed from First_Name
     String lastName;  // Changed from Last_Name
     String email;     // Changed from Email
     String phoneNumber; // Changed from Phone_number
     String address;    // Changed from Address
     Gender gender;     // Changed from Gender
     String urlImage;   // Changed from url_image

}

