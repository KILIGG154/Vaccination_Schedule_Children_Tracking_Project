package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProtocolDetailResponse {
    private Long detailId;
    private int doseNumber;
    private int intervalDays;
}
