package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResponseVaccineCombo
{
    private int id;

    private String comboName;


    private String description;

    private double total;

    private boolean status;
}