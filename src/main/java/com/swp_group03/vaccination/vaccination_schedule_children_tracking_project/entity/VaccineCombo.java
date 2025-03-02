package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Vaccine_Combo")
public class VaccineCombo {
    @Id
    @Column(name = "ComboId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 100)
    @Column(name = "ComboName", length = 100)
    private String comboName;

    @Size(max = 1000)
    @Column(name = "Description", length = 1000)
    private String description;

    @Column(name = "Status")
    private boolean status;

    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY)
    private Set<VaccineComboDetail> vaccineComboDetails;



}