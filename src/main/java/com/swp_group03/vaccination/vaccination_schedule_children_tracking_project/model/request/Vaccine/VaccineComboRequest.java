package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineComboRequest {

    private String comboName;


    private String description;


    private boolean status;
}