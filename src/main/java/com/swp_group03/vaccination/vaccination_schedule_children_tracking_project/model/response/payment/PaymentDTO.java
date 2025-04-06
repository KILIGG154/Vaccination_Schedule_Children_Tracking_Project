package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.PaymentStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private int id;
    private PaymentStatus status;
    private String paymentMethod;

    public PaymentDTO(Payment payment) {
        if (payment != null) {
            this.id = payment.getId();
            this.status = payment.getStatus();
            this.paymentMethod = payment.getPaymentMethod();
        }
    }
}
