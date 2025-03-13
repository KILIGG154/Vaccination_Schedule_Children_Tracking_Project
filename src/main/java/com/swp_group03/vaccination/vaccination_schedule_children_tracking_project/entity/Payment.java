package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @Column(name = "PaymentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Payment_Date")
    private Date paymentDate;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "Payment_Method")
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookingId")
    private Booking booking;


    public Payment() {
    }

    public Payment(Date paymentDate, boolean status, String paymentMethod, Booking booking) {
        this.paymentDate = paymentDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
