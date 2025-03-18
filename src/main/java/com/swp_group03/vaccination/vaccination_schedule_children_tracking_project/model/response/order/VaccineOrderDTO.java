package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Booking;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VaccineOrderDTO {
    private int id;
    private Date orderDate;
    private List<VaccineOrderDetailDTO> orderDetail;
    public VaccineOrderDTO(VaccineOrder vaccineOrder) {
        this.id = vaccineOrder.getId();
        this.orderDate = vaccineOrder.getDateOrderVaccine();
        this.orderDetail = vaccineOrder.getVaccineOrderDetails().stream().map(VaccineOrderDetailDTO::new).collect(Collectors.toList());
    }
}
