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
public class IntrospectRequest {
        String token;
}
