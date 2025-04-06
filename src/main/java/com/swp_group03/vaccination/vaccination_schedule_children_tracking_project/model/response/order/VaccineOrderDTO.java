package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.payment.PaymentDTO;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccineOrderDTO {
    private int id;
    private int bookingId;
    private Date orderDate;
    private PaymentDTO payment;
    private OrderStatus status;
    private List<VaccineOrderDetailDTO> orderDetail;
    
    public VaccineOrderDTO(VaccineOrder vaccineOrder) {
        this.id = vaccineOrder.getId();
        if (vaccineOrder.getBooking() != null) {
            this.bookingId = vaccineOrder.getBooking().getBookingId();
        }
        this.orderDate = vaccineOrder.getOrderDate();
        this.orderDetail = vaccineOrder.getVaccineOrderDetails().stream()
                .map(VaccineOrderDetailDTO::new)
                .collect(Collectors.toList());
        this.payment = vaccineOrder.getPayment() != null ? new PaymentDTO(vaccineOrder.getPayment()) : null;
        this.status = vaccineOrder.getStatus();
    }

}
