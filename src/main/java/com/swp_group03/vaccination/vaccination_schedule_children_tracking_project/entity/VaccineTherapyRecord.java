package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@Table(name = "vaccine_therapy_record")
public class VaccineTherapyRecord {
    @Id
    @Column(name = "therapy_vaccine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tVaccineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dose_id")
    private VaccineProtocolDose dose;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "scheduled_date")
    private LocalDate therapyDate;

    @Enumerated(EnumType.STRING) // Use STRING to store the enum as a string in the database
    @Column(name = "status")
    private VaccineTherapyStatus status;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_at")
    private LocalDateTime updateAt;


    public VaccineTherapyRecord() {
    }

    public VaccineTherapyRecord(Booking booking, VaccineProtocolDose dose, LocalDate therapyDate, VaccineTherapyStatus status, LocalDateTime createAt, LocalDateTime updateAt) {
        this.booking = booking;
        this.dose = dose;
        this.therapyDate = therapyDate;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
