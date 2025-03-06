package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment_Method;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class PaymentRequest {

    private Date paymentDate;

    private boolean status;

    private Payment_Method paymentMethod;

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

    public Payment_Method getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment_Method paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
