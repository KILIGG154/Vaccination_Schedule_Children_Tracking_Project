package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.payment;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OrderStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;

import java.util.Date;

public class PaymentDTO {
    private int id;
    private Date paymentDate;
    private OrderStatus status;
    private String paymentMethod;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.status = payment.getStatus();
        this.paymentMethod = payment.getPaymentMethod();
    }

}
