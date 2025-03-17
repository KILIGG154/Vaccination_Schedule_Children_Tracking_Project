package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OrderStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;

import java.util.Date;

public class PaymentRequest {


    private OrderStatus status;

    private String paymentMethod;

    private VaccineOrder vaccineOrder;

    public PaymentRequest() {
    }

    public PaymentRequest(OrderStatus status, String paymentMethod, VaccineOrder vaccineOrder) {
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.vaccineOrder = vaccineOrder;
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

    public VaccineOrder getVaccineOrder() {
        return vaccineOrder;
    }

    public void setVaccineOrder(VaccineOrder vaccineOrder) {
        this.vaccineOrder = vaccineOrder;
    }
}
