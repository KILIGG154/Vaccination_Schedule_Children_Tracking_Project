package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ComboStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private ComboStatus status = ComboStatus.AVAILABLE;

    @Size(max = 100)
    @Column(name = "ComboCategory", length = 100)
    private String comboCategory;

    @Column(name = "SaleOff")
    private double saleOff;

    @Column(name = "Dose")
    private int dose;


    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VaccineComboDetail> vaccineComboDetails = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "VaccineOrderId")
    @JsonIgnore
    private VaccineOrder vaccineOrder;

    public VaccineCombo(String comboName, String description, ComboStatus status) {
        this.comboName = comboName;
        this.description = description;
        this.status = status;
    }
    
    public void addComboDetail(VaccineComboDetail detail) {
        vaccineComboDetails.add(detail);
        detail.setCombo(this);
        detail.setComboId(this.id);
    }
    
    public void removeComboDetail(VaccineComboDetail detail) {
        vaccineComboDetails.remove(detail);
        detail.setCombo(null);
        detail.setComboId(0);
    }
}