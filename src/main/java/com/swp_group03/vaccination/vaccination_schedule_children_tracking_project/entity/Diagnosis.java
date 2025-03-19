package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private int diagnosisId;

    @Column(name = "Description")
    private String description;

    @Column(name = "Treatment")
    private String treatment;

    @Column(name = "Result")
    private String result;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookingId", nullable = false)
    private Booking booking;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountId", nullable = false)
    private Account account;    //Đây là DoctorDoctor

    public Diagnosis() {
    }

    public Diagnosis(String description, String treatment, String result, Booking booking, Account account) {
        this.description = description;
        this.treatment = treatment;
        this.result = result;
        this.booking = booking;
        this.account = account;
    }


}
