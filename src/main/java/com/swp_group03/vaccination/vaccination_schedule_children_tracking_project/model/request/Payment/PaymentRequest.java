package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OrderStatus;

import java.util.Date;

public class PaymentRequest {


    private OrderStatus status;

    private String paymentMethod;

    public PaymentRequest() {
    }

    public PaymentRequest(OrderStatus status, String paymentMethod) {
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
