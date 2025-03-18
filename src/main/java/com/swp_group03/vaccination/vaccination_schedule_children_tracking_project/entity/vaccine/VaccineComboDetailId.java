package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter 
public class VaccineComboDetailId implements Serializable {
    private int comboId;
    private int vaccineId;

    public VaccineComboDetailId() {}

    public VaccineComboDetailId(int comboId, int vaccineId) {
        this.comboId = comboId;
        this.vaccineId = vaccineId;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineComboDetailId that = (VaccineComboDetailId) o;
        return Objects.equals(comboId, that.comboId) &&
                Objects.equals(vaccineId, that.vaccineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comboId, vaccineId);
    }
}