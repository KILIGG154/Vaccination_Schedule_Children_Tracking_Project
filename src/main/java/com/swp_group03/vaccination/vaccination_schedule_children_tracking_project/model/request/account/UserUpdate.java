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
public class UserUpdate {

    @Size(min = 3, max = 16, message = "PASSWORD_INVALID")
     String password;
     String firstName; // Changed from First_Name
     String lastName;  // Changed from Last_Name

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Please enter right email")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự !!")
     String email;     // Changed from Email

    @Pattern(regexp = "^0[0-9]{9}$", message = "Enter correct " +
            "Phone number")
     String phoneNumber; // Changed from Phone_number

    @Size(max = 100, message = "Địa chỉ không được vượt quá 100 ký tự !!")
     String address;    // Changed from Address

     Gender gender;     // Changed from Gender
     String urlImage;   // Changed from url_image

}

