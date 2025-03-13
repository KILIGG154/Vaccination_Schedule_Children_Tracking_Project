package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingRequest {


    private Date appointmentDate;
    private Child child;
    private List<Payment> payments;
    private boolean status;

    public BookingRequest() {

    }

    public BookingRequest(Date appointmentDate, Child child, List<Payment> payments, boolean status) {
        this.appointmentDate = appointmentDate;
        this.child = child;
        this.payments = payments;
        this.status = status;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
