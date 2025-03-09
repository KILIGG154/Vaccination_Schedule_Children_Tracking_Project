package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "Vaccine_Combo_Detail")
@IdClass(VaccineComboDetailId.class) // Thêm annotation này
@Getter
@Setter
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
    @Column(name = "ComboCategory", length = 100)
    private String comboCategory;

    @Column(name = "SaleOff")
    private double saleOff;

    @Column(name = "Total")
    private double total;


    public VaccineComboDetail() {
    }

    public VaccineComboDetail(int vaccineId, int comboId, Vaccine vaccine, VaccineCombo combo, int dose, String comboCategory, double saleOff, double total) {
        this.vaccineId = vaccineId;
        this.comboId = comboId;
        this.vaccine = vaccine;
        this.combo = combo;
        this.dose = dose;
        this.comboCategory = comboCategory;
        this.saleOff = saleOff;
        this.total = total;
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

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public VaccineCombo getCombo() {
        return combo;
    }

    public void setCombo(VaccineCombo combo) {
        this.combo = combo;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getComboCategory() {
        return comboCategory;
    }

    public void setComboCategory(String comboCategory) {
        this.comboCategory = comboCategory;
    }

    public double getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(double saleOff) {
        this.saleOff = saleOff;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}