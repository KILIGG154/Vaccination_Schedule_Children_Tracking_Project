package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine;

import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolDetailRequest {
    private int doseNumber;
    private int intervalDays;
}
