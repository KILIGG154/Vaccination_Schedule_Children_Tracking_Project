package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    String username;

    @Size(min = 3, max = 16, message = "PASSWORD_INVALID")
    String password;
}
