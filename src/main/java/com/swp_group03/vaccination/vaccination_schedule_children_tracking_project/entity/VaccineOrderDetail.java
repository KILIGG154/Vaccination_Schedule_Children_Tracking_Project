package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "VaccineOrderDetail")
@IdClass(VaccineOrderDetailId.class)
public class VaccineOrderDetail {

    @Id
    @Column(name = "OrderId")
    private int vaccineOrderID;

    @Id
    @Column(name = "VaccineID")
    private int id;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Total")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderId", insertable = false, updatable = false)
    private VaccineOrder vaccineOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VaccineId", insertable = false, updatable = false)
    private Vaccine vaccine;

    public VaccineOrderDetail() {
    }

    public int getVaccineOrderID() {
        return vaccineOrderID;
    }

    public void setVaccineOrderID(int vaccineOrderID) {
        this.vaccineOrderID = vaccineOrderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
}
