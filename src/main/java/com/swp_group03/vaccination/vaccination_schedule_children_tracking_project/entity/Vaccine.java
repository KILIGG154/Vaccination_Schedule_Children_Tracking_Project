package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
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
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @Size(max = 255)
    @Column(name = "Manufacturer")
    private String manufacturer;

    @Size(max = 255)
    @Column(name = "Dosage")
    private String dosage;

    @Lob
    @Column(name = "Contraindications")
    private String contraindications;

    @Lob
    @Column(name = "Precautions")
    private String precautions;

    @Lob
    @Column(name = "Interactions")
    private String interactions;

    @Lob
    @Column(name = "AdverseReactions")
    private String adverseReactions;

    @Lob
    @Column(name = "StorageConditions")
    private String storageConditions;

    @Lob
    @Column(name = "Recommended")
    private String recommended;

    @Lob
    @Column(name = "PreVaccination")
    private String preVaccination;

    @Lob
    @Column(name = "Compatibility")
    private String compatibility;

    @Size(max = 255)
    @Lob
    @Column(name = "Imagine_URL")
    private String imagineUrl;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "ExpirationDate")
    private LocalDate expirationDate;

    @Column(name = "Price")
    private double unitPrice;

    @Column(name = "SalePrice")
    private double salePrice;

    @Size(max = 50)
    @Column(name = "Status", length = 50)
    private String status;

    @OneToMany(mappedBy = "vaccine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<VaccineComboDetail> vaccineComboDetails = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    private VacineCategory categoryID;



}