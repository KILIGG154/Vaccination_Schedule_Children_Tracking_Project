package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Vaccine_Category")
public class VaccineCategory {
    @Id
    @Column(name = "CategoryId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Size(max = 100)
    @Nationalized
    @Column(name = "CategoryName", length = 100)
    private String categoryName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "categoryId",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Vaccine> vaccines = new HashSet<>();

    public void addVaccine(Vaccine vaccine) {
        this.vaccines.add(vaccine);
        vaccine.setCategoryId(this);
    }

    public void removeVaccine(Vaccine vaccine) {
        this.vaccines.remove(vaccine);
        vaccine.setCategoryId(null);
    }

//    @OneToMany(mappedBy = "categoryID")
//    private Set<Vaccine> vaccines = new LinkedHashSet<>();



}