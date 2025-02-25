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
@Table(name = "Vaccine_Combo")
public class VaccineCombo {
    @Id
    @Column(name = "ComboId", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "ComboName", nullable = false, length = 100)
    private String comboName;

    @Size(max = 1000)
    @NotNull
    @Column(name = "Description", nullable = false, length = 1000)
    private String description;

    @Column(name = "Status")
    private Boolean status;

}