package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @Column(name = "PaymentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Status")
    private OrderStatus status = OrderStatus.IN_PROCESS;

    @Column(name = "Payment_Method")
    private String paymentMethod;

    @OneToOne
    @JoinColumn(name = "OrderId",referencedColumnName = "OrderId")
    private VaccineOrder vaccineOrder;

    public Payment() {
    }

    public Payment(Date paymentDate, OrderStatus status, String paymentMethod, VaccineOrder vaccineOrder) {

        this.status = status;
        this.paymentMethod = paymentMethod;
        this.vaccineOrder = vaccineOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public VaccineOrder getVaccineOrder() {
        return vaccineOrder;
    }

    public void setVaccineOrder(VaccineOrder vaccineOrder) {
        this.vaccineOrder = vaccineOrder;
    }
}
