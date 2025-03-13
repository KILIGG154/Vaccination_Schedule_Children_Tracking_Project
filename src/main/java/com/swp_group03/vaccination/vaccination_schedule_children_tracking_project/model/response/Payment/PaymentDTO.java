package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import jakarta.persistence.*;

import java.util.Date;

public class PaymentDTO {
    private int id;
    private Date paymentDate;
    private boolean status;
    private String paymentMethod;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.paymentDate = payment.getPaymentDate();
        this.status = payment.isStatus();
        this.paymentMethod = payment.getPaymentMethod();
    }

}
