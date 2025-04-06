package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ComboStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

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

    private ComboStatus status;
    
    private List<ResponseVaccineDetails> vaccineDetails;
}