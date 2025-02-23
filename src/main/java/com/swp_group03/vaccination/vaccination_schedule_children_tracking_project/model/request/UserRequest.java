package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {
    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 3, max = 16, message = "PASSWORD_INVALID")
    private String password;
    private String firstName; // Changed from First_Name
    private String lastName;  // Changed from Last_Name
    private String email;     // Changed from Email
    private String phoneNumber; // Changed from Phone_number
    private String address;    // Changed from Address
    private String gender;     // Changed from Gender
    private String urlImage;   // Changed from url_image
}
