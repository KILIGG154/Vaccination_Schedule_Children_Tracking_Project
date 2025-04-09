package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineCombo;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Booking")
    @JsonManagedReference
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "VaccineComboID")
    @JsonIgnore
    private VaccineCombo vaccineCombo;

    @OneToOne
    @JoinColumn(name = "PaymentID")
    @JsonIgnore
    private Payment payment;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.REJECTED;

    @OneToMany(mappedBy = "vaccineOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
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

    public VaccineOrder(LocalDate orderDate, Booking booking, VaccineCombo vaccineCombo, Payment payment, Set<VaccineOrderDetail> vaccineOrderDetails) {
        this.orderDate = orderDate;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
