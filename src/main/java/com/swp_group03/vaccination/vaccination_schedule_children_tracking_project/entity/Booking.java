package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "Appointmen_tDate")
    private Date appointmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId")
    private Child child;


    @Column(name = "Status")
    private boolean status;

    @OneToMany(mappedBy = "booking",fetch = FetchType.LAZY)
    @Column(name = "Vaccine_Order")
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

    public Booking(Date appointmentDate, Child child, boolean status, List<VaccineOrder> vaccineOrders) {
        this.appointmentDate = appointmentDate;
        this.child = child;
        this.status = status;
        this.vaccineOrders = vaccineOrders;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<VaccineOrder> getVaccineOrders() {
        return vaccineOrders;
    }

    public void setVaccineOrders(List<VaccineOrder> vaccineOrders) {
        this.vaccineOrders = vaccineOrders;
    }
}
