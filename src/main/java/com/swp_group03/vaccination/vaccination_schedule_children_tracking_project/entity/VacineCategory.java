package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Vacine_Category")
public class VacineCategory {
    @Id
    @Column(name = "CategoryId", nullable = false)
    private Integer categoryId;

    @Size(max = 100)
    @Column(name = "CategoryName", length = 100)
    private String categoryName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "Description")
    private String description;

//    @OneToMany(mappedBy = "categoryID")
//    private Set<Vaccine> vaccines = new LinkedHashSet<>();



}