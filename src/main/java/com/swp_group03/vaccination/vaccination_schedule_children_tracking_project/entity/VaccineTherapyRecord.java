package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @Temporal(TemporalType.DATE) // Chỉ lưu ngày, không có giờ
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "scheduled_date")
    private Date therapyDate;

    @Enumerated(EnumType.STRING) // Use STRING to store the enum as a string in the database
    @Column(name = "status")
    private VaccineTherapyStatus status;


    @Temporal(TemporalType.DATE) // Chỉ lưu ngày, không có giờ
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_at")
    private Date createAt;

    @Temporal(TemporalType.DATE) // Chỉ lưu ngày, không có giờ
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_at")
    private Date updateAt;


    public VaccineTherapyRecord() {
    }

    public VaccineTherapyRecord(Booking booking, VaccineProtocolDose dose, Date therapyDate, VaccineTherapyStatus status, Date createAt, Date updateAt) {
        this.booking = booking;
        this.dose = dose;
        this.therapyDate = therapyDate;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
