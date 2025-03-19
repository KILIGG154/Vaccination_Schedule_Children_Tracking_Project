package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProtocolRequest {
    private String name;
    private String description;
    private List<ProtocolDetailRequest> details;
}
