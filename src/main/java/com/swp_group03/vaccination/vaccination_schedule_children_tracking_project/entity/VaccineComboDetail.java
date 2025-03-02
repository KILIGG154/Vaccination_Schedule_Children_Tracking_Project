package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Vaccine_Combo_Detail")
public class VaccineComboDetail {

    @EmbeddedId
    private VaccineComboDetailId id;

    @MapsId("comboId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comboId")
    private VaccineCombo combo;

    @MapsId("vaccineId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vaccineId")
    private Vaccine vaccine;

    @Column(name = "Dose")
    private Integer dose;

    @Size(max = 100)
    @Column(name = "Age_Group", length = 100)
    private String ageGroup;

    @Column(name = "SaleOff")
    private Double saleOff;


    public VaccineComboDetail() {
    }

    public VaccineComboDetail(VaccineComboDetailId id, VaccineCombo combo, Vaccine vaccine, Integer dose, String ageGroup, Double saleOff) {
        this.id = id;
        this.combo = combo;
        this.vaccine = vaccine;
        this.dose = dose;
        this.ageGroup = ageGroup;
        this.saleOff = saleOff;
    }

    public VaccineComboDetailId getId() {
        return id;
    }

    public void setId(VaccineComboDetailId id) {
        this.id = id;
    }

    public VaccineCombo getCombo() {
        return combo;
    }

    public void setCombo(VaccineCombo combo) {
        this.combo = combo;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Double getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(Double saleOff) {
        this.saleOff = saleOff;
    }
}