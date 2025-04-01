package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine;

public class VaccineUpdate {
    private int quantity;
    private int unitPrice;
    private int salePrice;

    public VaccineUpdate() {
    }

    public VaccineUpdate(int quantity, int unitPrice, int salePrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
}
