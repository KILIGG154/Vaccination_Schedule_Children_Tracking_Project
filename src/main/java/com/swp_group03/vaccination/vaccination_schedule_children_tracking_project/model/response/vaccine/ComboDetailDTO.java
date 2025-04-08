package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineComboDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComboDetailDTO {
    private int id;
    private int vaccineId;
    private int comboId;
    private String vaccineName;
    private String manafacture;
    private int totalDose;

    public ComboDetailDTO(VaccineComboDetail vaccineComboDetail) {
        this.id = vaccineComboDetail.getDetailId();
        this.vaccineId = vaccineComboDetail.getVaccine().getId();
        this.comboId = vaccineComboDetail.getCombo().getId();
        this.vaccineName = vaccineComboDetail.getVaccine().getName();
        this.manafacture = vaccineComboDetail.getVaccine().getManufacturer();
        this.totalDose = vaccineComboDetail.getVaccine().getTotalDose();

    }

    @Override
    public String toString() {
        return "ComboDetailDTO{" +
                "id=" + id +
                ", vaccineId=" + vaccineId +
                ", comboId=" + comboId +
                ", vaccineName='" + vaccineName + '\'' +
                ", manafacture='" + manafacture + '\'' +
                '}';
    }
}
