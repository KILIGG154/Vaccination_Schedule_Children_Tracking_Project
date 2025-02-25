package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Combo_Category")
public class ComboCategory {
    @Id
    @Column(name = "CategporyId", nullable = false)
    private Integer id;

    @Size(max = 1000)
    @NotNull
    @Column(name = "Description", nullable = false, length = 1000)
    private String description;

    @Column(name = "Status")
    private Boolean status;

}