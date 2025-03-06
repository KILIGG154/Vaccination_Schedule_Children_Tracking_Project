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
public class WorkingScheduleId implements Serializable {
 //   private static final long serialVersionUID = 2769469257151551151L;


    private int dateId;

    private String accountId;

    public WorkingScheduleId() {
    }
    public WorkingScheduleId(int dateId, String accountId) {
        this.dateId = dateId;
        this.accountId = accountId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        WorkingScheduleId entity = (WorkingScheduleId) o;
//        return Objects.equals(this.accountId, entity.accountId) &&
//                Objects.equals(this.dateId, entity.dateId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingScheduleId that = (WorkingScheduleId) o;
        return Objects.equals(dateId, that.dateId) &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateId, accountId);
    }

}