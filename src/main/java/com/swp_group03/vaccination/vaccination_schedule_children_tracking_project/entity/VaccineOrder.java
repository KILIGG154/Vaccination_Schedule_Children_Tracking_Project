package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vaccine_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccineOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vaccineOrderID;

    @Column(name = "Type")
    private boolean type;

    @Column(name = "Total")
    private double total;

    @Column(name = "Status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID")
    private Account account;

    @OneToOne(mappedBy = "vaccineOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VaccineCombo vaccineCombo;
}
