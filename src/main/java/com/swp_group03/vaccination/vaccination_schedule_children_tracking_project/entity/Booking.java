package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingId")
    private int bookingId;

    @Column(name = "Appointment_Date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId")
    @JsonIgnore
    private Child child;

//
//    @Column(name = "Status")
//    private boolean status;

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "reaction")
    private String reaction; // Phản ứng sau tiêm

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Diagnosis diagnosis;

    @OneToMany(mappedBy = "booking",fetch = FetchType.LAZY)
    @Column(name = "Vaccine_Order")
    @JsonIgnore
    private List<VaccineOrder> vaccineOrders;

    public void addVaccineOrder(VaccineOrder vaccineOrder) {

        if (vaccineOrders == null) {
            vaccineOrders = new ArrayList<>();
        }
        vaccineOrders.add(vaccineOrder);
        vaccineOrder.setBooking(this);
    }

    public void removeVaccineOrder(VaccineOrder vaccineOrder) {
        if (vaccineOrders != null) {
            vaccineOrders.remove(vaccineOrder);
            vaccineOrder.setBooking(null);
        }
    }

    public Booking() {

    }

    public Booking(Date appointmentDate, Child child, BookingStatus status, List<VaccineOrder> vaccineOrders, String reaction) {
        this.appointmentDate = appointmentDate;
        this.child = child;
        this.status = status;
        this.vaccineOrders = vaccineOrders;
        this.reaction = reaction;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public List<VaccineOrder> getVaccineOrders() {
        return vaccineOrders;
    }

    public void setVaccineOrders(List<VaccineOrder> vaccineOrders) {
        this.vaccineOrders = vaccineOrders;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }
}
