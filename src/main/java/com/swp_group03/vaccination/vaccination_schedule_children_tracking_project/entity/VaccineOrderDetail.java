package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.Vaccine;
import jakarta.persistence.*;

@Entity
@Table(name = "VaccineOrderDetail")
public class VaccineOrderDetail {

    @Id
    @Column(name = "DetailID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VaccineOrderID")
    @JsonIgnore
    private VaccineOrder vaccineOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VaccineID")
    @JsonIgnore
    private Vaccine vaccine;

    @Column(name = "Quantity")
    private int qunatity;

    @Column(name = "TotalPrice")
    private double totalPrice;
    public VaccineOrderDetail() {
    }

    public VaccineOrderDetail(VaccineOrder vaccineOrder, Vaccine vaccine, int qunatity, double totalPrice) {
        this.vaccineOrder = vaccineOrder;
        this.vaccine = vaccine;
        this.qunatity = qunatity;
        this.totalPrice = totalPrice;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public VaccineOrder getVaccineOrder() {
        return vaccineOrder;
    }

    public void setVaccineOrder(VaccineOrder vaccineOrder) {
        this.vaccineOrder = vaccineOrder;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
