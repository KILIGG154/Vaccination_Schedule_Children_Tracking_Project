package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCombo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResponseVaccineDetails {
    private int vaccineId;
    private int comboId;
    private int dose;
//    private Vaccine vaccine;            Tính ra là xóa luôn r đấy chứ :>>
//    private VaccineCombo vaccineCombo;  Tính ra là xóa luôn r đấy chứ :>>
    private String comboCategory;
    private double saleOff;
    private String vaccineName;
    private String comboName;
//    private double totalCombo;    Sai nên nên cmt lại
    private String manufacturer;
    private double total;
    private String description;
}