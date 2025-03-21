package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    @Column(name = "Total")
    private double total;

    @Column(name = "Status")
    private boolean status;

    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<VaccineComboDetail> vaccineComboDetails = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "VaccineOrderId")
    @JsonIgnore
    private VaccineOrder vaccineOrder;

    public VaccineCombo(String comboName, String description, boolean status) {
        this.comboName = comboName;
        this.description = description;
        this.status = status;
    }

}