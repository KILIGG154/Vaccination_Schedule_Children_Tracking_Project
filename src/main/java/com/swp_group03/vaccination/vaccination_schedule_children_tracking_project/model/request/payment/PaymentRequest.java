package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.PaymentStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;


public class PaymentRequest {

    private String token;

    private double amount;

    private PaymentStatus status;

    private String paymentMethod;

    private VaccineOrder vaccineOrder;

    public PaymentRequest() {
    }

    public PaymentRequest(String token, double amount, PaymentStatus status, String paymentMethod, VaccineOrder vaccineOrder) {
        this.token = token;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.vaccineOrder = vaccineOrder;
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

    public VaccineOrder getVaccineOrder() {
        return vaccineOrder;
    }

    public void setVaccineOrder(VaccineOrder vaccineOrder) {
        this.vaccineOrder = vaccineOrder;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
