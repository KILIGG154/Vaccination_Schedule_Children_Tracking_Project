package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Combo_Category_Detail")
public class ComboCategoryDetail {
    @EmbeddedId
    private ComboCategoryDetailId id;

    @MapsId("categporyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CategporyId", nullable = false)
    private ComboCategory categpory;

}