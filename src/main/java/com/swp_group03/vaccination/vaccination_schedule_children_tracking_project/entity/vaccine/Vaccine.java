package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vaccine")
public class Vaccine {
    @Id
    @Column(name = "VaccineID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 255)
    @NotNull
    @Column(name = "Name")
    @Nationalized
    private String name;

    @Lob
    @Column(name = "Description")
    @Nationalized
    private String description;

    @Size(max = 255)
    @Column(name = "Manufacturer")
    @Nationalized
    private String manufacturer;

    @Size(max = 255)
    @Column(name = "Dosage") //Đây là cột liều lượng cho một lần tiêm á!!!
    @Nationalized
    private String dosage;

    @Lob
    @Column(name = "Contraindications")
    @Nationalized
    private String contraindications;

    @Lob
    @Column(name = "Precautions")
    @Nationalized
    private String precautions;

    @Lob
    @Column(name = "Interactions")
    @Nationalized
    private String interactions;

    @Lob
    @Column(name = "StorageConditions")
    @Nationalized
    private String storageConditions;

    @Lob
    @Column(name = "Recommended")
    @Nationalized
    private String recommended;

    @Lob
    @Column(name = "PreVaccination")
    @Nationalized
    private String preVaccination;

    @Lob
    @Column(name = "Compatibility")
    @Nationalized
    private String compatibility;

    @Size(max = 255)
    @Lob
    @Column(name = "Imagine_URL")
    private String imagineUrl;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private double unitPrice;

    @Column(name = "SalePrice")
    private double salePrice;

    @Column(name = "TotalDoses")
    private int totalDose;


    @Column(name = "Status", length = 50)
    @Enumerated(EnumType.STRING)
    private VaccineStatus status = VaccineStatus.ACTIVE;

    @OneToMany(mappedBy = "vaccine", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<VaccineComboDetail> vaccineComboDetails = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CategoryID")
    private VaccineCategory categoryId;

    @OneToMany(mappedBy = "vaccine",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<VaccineOrderDetail> vaccineOrderDetails = new HashSet<>();

    public void addVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.add(vaccineOrderDetail);
        vaccineOrderDetail.setVaccine(this);
    }

    public void removeVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.remove(vaccineOrderDetail);
        vaccineOrderDetail.setVaccine(null);
    }

    public void addVaccineComboDetail(VaccineComboDetail detail) {
        vaccineComboDetails.add(detail);
        detail.setVaccine(this);
        detail.setVaccineId(this.id);
    }

    public void removeVaccineComboDetail(VaccineComboDetail detail) {
        vaccineComboDetails.remove(detail);
        detail.setVaccine(null);
        detail.setVaccineId(0);
    }

}