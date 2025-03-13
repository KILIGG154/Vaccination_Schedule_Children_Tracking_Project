package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment;

import java.util.Date;

public class PaymentRequest {

    private Date paymentDate;

    private boolean status;

    private String paymentMethod;

    public PaymentRequest(Date paymentDate, boolean status) {
        this.paymentDate = paymentDate;
        this.status = status;
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
}
