package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.PaymentStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;


public class PaymentRequest {


    private double amount;

    private PaymentStatus status;

    private String paymentMethod;

    public PaymentRequest() {
    }

    public PaymentRequest(double amount, PaymentStatus status, String paymentMethod) {
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
