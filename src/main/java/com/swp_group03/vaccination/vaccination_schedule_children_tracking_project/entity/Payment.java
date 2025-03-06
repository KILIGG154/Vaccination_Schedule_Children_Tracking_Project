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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PaymentMethodId")
    private Payment_Method paymentMethod;

    public Payment(Date paymentDate, boolean status) {
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public Payment() {
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

    public Payment_Method getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment_Method paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
