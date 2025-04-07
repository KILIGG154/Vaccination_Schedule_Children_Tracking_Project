package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Vaccine_Combo_Detail")
@Getter
@Setter
public class VaccineComboDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetailId")
    private int detailId;

    @Column(name = "VaccineId")
    private int vaccineId;

    @Column(name = "ComboId")
    private int comboId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccineId", insertable = false, updatable = false)
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comboId", insertable = false, updatable = false)
    private VaccineCombo combo;



//    @Column(name = "Total_Of_Combo")
//    private double totalCombo;


    public VaccineComboDetail() {
    }

    public VaccineComboDetail(int detailId, int vaccineId, int comboId, Vaccine vaccine, VaccineCombo combo) {
        this.detailId = detailId;
        this.vaccineId = vaccineId;
        this.comboId = comboId;
        this.vaccine = vaccine;
        this.combo = combo;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
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
        if (vaccine != null) {
            this.vaccineId = vaccine.getId();
        }
    }

    public VaccineCombo getCombo() {
        return combo;
    }

    public void setCombo(VaccineCombo combo) {
        this.combo = combo;
        if (combo != null) {
            this.comboId = combo.getId();
        }
    }



//    public double getTotal() {
//        return totalCombo;
//    }
//
//    public void setTotal(double totalCombo) {
//        this.totalCombo = totalCombo;
//    }
}