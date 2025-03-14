package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vaccine_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VaccineOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private int vaccineOrderID;

    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Booking")
    private Booking booking;

    @OneToOne(mappedBy = "vaccineOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VaccineCombo vaccineCombo;

    @OneToOne(mappedBy = "vaccineOrder",fetch = FetchType.LAZY)
    private Payment payment;

    @OneToMany(mappedBy = "vaccineOrder")
    private List<VaccineOrderDetail> vaccineOrderDetails;

    public void addVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.add(vaccineOrderDetail);
        vaccineOrderDetail.setVaccineOrder(this);
    }

    public void removeVaccineOrderDetail(VaccineOrderDetail vaccineOrderDetail) {
        vaccineOrderDetails.remove(vaccineOrderDetail);
        vaccineOrderDetail.setVaccineOrder(null);
    }


}
