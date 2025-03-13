package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineCombeDetailRequest {
//    private int vaccineId;
//    private int comboId;
    private int dose;
    private String comboCategory;
    private double saleOff;
}