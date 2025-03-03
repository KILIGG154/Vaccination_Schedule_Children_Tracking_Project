package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Vaccine_Combo_Detail")
@IdClass(VaccineComboDetailId.class) // Thêm annotation này
public class VaccineComboDetail {

    @Id // Thay thế @EmbeddedId bằng @Id cho từng trường
    @Column(name = "vaccineId")
    private int vaccineId;
    
    @Id // Thêm @Id cho trường thứ hai
    @Column(name = "comboId")
    private int comboId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineId", insertable = false, updatable = false) // Thêm insertable=false, updatable=false
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comboId", insertable = false, updatable = false) // Thêm insertable=false, updatable=false
    private VaccineCombo combo;

    @Column(name = "Dose")
    private int dose;

    @Size(max = 100)
    @Column(name = "Age_Group", length = 100)
    private String ageGroup;

    @Column(name = "SaleOff")
    private double saleOff;


    public VaccineComboDetail() {
    }

    public VaccineComboDetail(VaccineCombo combo, Vaccine vaccine, Integer dose, String ageGroup, Double saleOff) {
        this.combo = combo;
        this.vaccine = vaccine;
        this.dose = dose;
        this.ageGroup = ageGroup;
        this.saleOff = saleOff;
    }

   
    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
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