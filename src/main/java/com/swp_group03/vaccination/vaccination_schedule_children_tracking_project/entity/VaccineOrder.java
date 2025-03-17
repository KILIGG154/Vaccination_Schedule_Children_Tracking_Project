package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VaccineOrder")
public class VaccineOrder {

    @Id
    @Column(name = "VaccineOrderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "OrderDate")
    @Temporal(TemporalType.DATE)
    private Date dateOrderVaccine;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Booking")
    private Booking booking;

    @OneToOne(mappedBy = "vaccineOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VaccineCombo vaccineCombo;

    @OneToOne(mappedBy = "vaccineOrder")
    private Payment payment;

    @OneToMany(mappedBy = "vaccineOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "VaccineOrderID")
    private Set<VaccineOrderDetail> vaccineOrderDetails = new HashSet<>();

    public void addVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.add(vaccineOrderDetail);
        vaccineOrderDetail.setVaccineOrder(this);
    }

    public void removeVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.remove(vaccineOrderDetail);
        vaccineOrderDetail.setVaccineOrder(null);
    }

    public VaccineOrder() {
    }

    public VaccineOrder(Date dateOrderVaccine, Booking booking, VaccineCombo vaccineCombo, Payment payment, Set<VaccineOrderDetail> vaccineOrderDetails) {
        this.dateOrderVaccine = dateOrderVaccine;
        this.booking = booking;
        this.vaccineCombo = vaccineCombo;
        this.payment = payment;
        this.vaccineOrderDetails = vaccineOrderDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOrderVaccine() {
        return dateOrderVaccine;
    }

    public void setDateOrderVaccine(Date dateOrderVaccine) {
        this.dateOrderVaccine = dateOrderVaccine;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public VaccineCombo getVaccineCombo() {
        return vaccineCombo;
    }

    public void setVaccineCombo(VaccineCombo vaccineCombo) {
        this.vaccineCombo = vaccineCombo;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<VaccineOrderDetail> getVaccineOrderDetails() {
        return vaccineOrderDetails;
    }

    public void setVaccineOrderDetails(Set<VaccineOrderDetail> vaccineOrderDetails) {
        this.vaccineOrderDetails = vaccineOrderDetails;
    }
}
