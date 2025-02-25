package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Vaccine_Combo_Detail")
public class VaccineComboDetail {
    @EmbeddedId
    private VaccineComboDetailId id;

    @MapsId("comboId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ComboId", nullable = false)
    private VaccineCombo combo;

    @Column(name = "Dose")
    private Integer dose;

    @Size(max = 100)
    @Column(name = "Age_Group", length = 100)
    private String ageGroup;

    @Column(name = "SaleOff")
    private Double saleOff;

}