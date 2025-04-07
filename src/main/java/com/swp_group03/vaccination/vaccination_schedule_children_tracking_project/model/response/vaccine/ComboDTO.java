package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ComboStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineCombo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ComboDTO {
    private int id;
    private String comboName;
    private String description;
    private double total;
    private ComboStatus status;
    private double saleOff;
    private String comboCategory;
    private int dose;
    private List<ComboDetailDTO> vaccineDetails;

    public ComboDTO(VaccineCombo vaccineCombo) {
        this.id = vaccineCombo.getId();
        this.comboName = vaccineCombo.getComboName();
        this.comboCategory = vaccineCombo.getComboCategory();
        this.saleOff = vaccineCombo.getSaleOff();
        this.description = vaccineCombo.getDescription();
        this.total = vaccineCombo.getTotal();
        this.status = vaccineCombo.getStatus();
        this.vaccineDetails = vaccineCombo.getVaccineComboDetails().stream()
                .map(ComboDetailDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ComboDTO{" +
                "id=" + id +
                ", comboName='" + comboName + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", status=" + status +
                ", saleOff=" + saleOff +
                ", comboCategory='" + comboCategory + '\'' +
                ", dose=" + dose +
                ", vaccineDetails.size=" + (vaccineDetails != null ? vaccineDetails.size() : 0) +
                '}';
    }
}
