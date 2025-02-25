package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class VaccineComboDetailId implements Serializable {
    private static final long serialVersionUID = -4753617762828333422L;
    @NotNull
    @Column(name = "ComboId", nullable = false)
    private Integer comboId;

    @NotNull
    @Column(name = "VaccineId", nullable = false)
    private Integer vaccineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VaccineComboDetailId entity = (VaccineComboDetailId) o;
        return Objects.equals(this.vaccineId, entity.vaccineId) &&
                Objects.equals(this.comboId, entity.comboId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaccineId, comboId);
    }

}