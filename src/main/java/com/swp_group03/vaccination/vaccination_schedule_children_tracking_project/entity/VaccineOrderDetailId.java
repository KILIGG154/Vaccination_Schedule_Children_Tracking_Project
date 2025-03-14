package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import java.io.Serializable;
import java.util.Objects;

public class VaccineOrderDetailId implements Serializable {
    private int vaccineOrderID;
    private int id;

    public VaccineOrderDetailId() {
    }

    public VaccineOrderDetailId(int vaccineOrderID, int id) {
        this.vaccineOrderID = vaccineOrderID;
        this.id = id;
    }

    public int getVaccineOrderID() {
        return vaccineOrderID;
    }

    public void setVaccineOrderID(int vaccineOrderID) {
        this.vaccineOrderID = vaccineOrderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccineOrderDetailId that = (VaccineOrderDetailId) o;
        return Objects.equals(vaccineOrderID, that.vaccineOrderID) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaccineOrderID, id);
    }

}
