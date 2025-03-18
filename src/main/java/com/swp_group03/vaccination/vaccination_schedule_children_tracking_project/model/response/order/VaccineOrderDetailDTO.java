package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccineOrderDetailDTO {
    private int detailID;
    private int vaccineOrderId;
    private int vaccineId;
    private String vaccineName;
    private int quantity;
    private double totalPrice;

    public VaccineOrderDetailDTO(VaccineOrderDetail vaccineOrderDetail) {
        this.detailID = vaccineOrderDetail.getDetailID();
        this.vaccineOrderId = vaccineOrderDetail.getVaccineOrder().getId();
        this.vaccineId = vaccineOrderDetail.getVaccine().getId();
        this.vaccineName = vaccineOrderDetail.getVaccine().getName();
        this.quantity = vaccineOrderDetail.getQunatity();
        this.totalPrice = vaccineOrderDetail.getTotalPrice();
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getVaccineOrderId() {
        return vaccineOrderId;
    }

    public void setVaccineOrderId(int vaccineOrderId) {
        this.vaccineOrderId = vaccineOrderId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
