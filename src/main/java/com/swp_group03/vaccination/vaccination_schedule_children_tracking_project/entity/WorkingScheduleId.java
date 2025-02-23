package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class WorkingScheduleId implements Serializable {
    private static final long serialVersionUID = 2769469257151551151L;
    @NotNull
    @Column(name = "Schedule_ID", nullable = false)
    private Integer scheduleId;

    @Size(max = 255)
    @NotNull
    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkingScheduleId entity = (WorkingScheduleId) o;
        return Objects.equals(this.accountId, entity.accountId) &&
                Objects.equals(this.scheduleId, entity.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, scheduleId);
    }

}