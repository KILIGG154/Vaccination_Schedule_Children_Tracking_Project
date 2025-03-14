package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.order;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;

import java.util.Date;
import java.util.List;

public class VaccineOrderRequest {
    private Date orderDate;

    private List<VaccineOrderDetail> orderDetail;
    public VaccineOrderRequest() {
    }

    public VaccineOrderRequest(Date orderDate, List<VaccineOrderDetail> orderDetail) {
        this.orderDate = orderDate;
        this.orderDetail = orderDetail;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<VaccineOrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<VaccineOrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
