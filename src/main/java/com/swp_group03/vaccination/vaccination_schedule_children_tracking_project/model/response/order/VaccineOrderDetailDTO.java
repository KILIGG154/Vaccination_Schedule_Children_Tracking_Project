package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import jakarta.persistence.*;

public class VaccineOrderDetailDTO {
    private int detailID;
    private VaccineOrder vaccineOrder;
    private Vaccine vaccine;
    private int qunatity;
    private double totalPrice;

    public VaccineOrderDetailDTO(VaccineOrderDetail vaccineOrderDetail) {
        this.detailID = vaccineOrderDetail.getDetailID();
        this.vaccineOrder = vaccineOrderDetail.getVaccineOrder();
        this.vaccine = vaccineOrderDetail.getVaccine();
        this.qunatity = vaccineOrderDetail.getQunatity();
        this.totalPrice = vaccineOrderDetail.getTotalPrice();
    }


    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public VaccineOrder getVaccineOrder() {
        return vaccineOrder;
    }

    public void setVaccineOrder(VaccineOrder vaccineOrder) {
        this.vaccineOrder = vaccineOrder;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
