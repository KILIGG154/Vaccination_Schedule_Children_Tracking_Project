package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class VaccineOrderRequest {

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOrderVaccine;
    private List<VaccineOrderDetail> orderDetail;

    public VaccineOrderRequest() {
    }

    public VaccineOrderRequest(Date dateOrderVaccine, List<VaccineOrderDetail> orderDetail) {
        this.dateOrderVaccine = dateOrderVaccine;
        this.orderDetail = orderDetail;
    }

    public Date getOrderDate() {
        return dateOrderVaccine;
    }

    public void setOrderDate(Date dateOrderVaccine) {
        this.dateOrderVaccine = dateOrderVaccine;
    }

    public List<VaccineOrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<VaccineOrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
