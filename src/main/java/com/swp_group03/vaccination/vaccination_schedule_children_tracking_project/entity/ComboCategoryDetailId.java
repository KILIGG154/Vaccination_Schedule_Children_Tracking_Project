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
public class ComboCategoryDetailId implements Serializable {
    private static final long serialVersionUID = 285436133686361021L;
    @NotNull
    @Column(name = "ComboId", nullable = false)
    private Integer comboId;

    @NotNull
    @Column(name = "CategporyId", nullable = false)
    private Integer categporyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ComboCategoryDetailId entity = (ComboCategoryDetailId) o;
        return Objects.equals(this.comboId, entity.comboId) &&
                Objects.equals(this.categporyId, entity.categporyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comboId, categporyId);
    }

}