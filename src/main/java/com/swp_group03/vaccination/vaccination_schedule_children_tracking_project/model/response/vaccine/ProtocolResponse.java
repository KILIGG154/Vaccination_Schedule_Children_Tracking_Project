package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProtocolResponse {
    private Long protocolId;
    private String name;
    private String description;
    private List<ProtocolDetailResponse> details;
}
