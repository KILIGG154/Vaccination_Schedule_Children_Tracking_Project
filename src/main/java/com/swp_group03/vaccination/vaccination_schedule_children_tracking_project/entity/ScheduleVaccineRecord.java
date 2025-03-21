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
@Table(name = "schedule_vaccine_record")
public class ScheduleVaccineRecord {

    @Id
    @Column(name = "vaccine_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dose_id")
    private VaccineProtocolDose dose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account; //Đây là Nurse

    @Temporal(TemporalType.DATE) // Chỉ lưu ngày, không có giờ
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "scheduled_date")
    private Date injectionDate;

    @Column(name = "status")
    private boolean status; //"đã tiêm" " chưa tiêm"

    public ScheduleVaccineRecord() {
    }

    public ScheduleVaccineRecord(Booking booking, VaccineProtocolDose dose, Account account, Date injectionDate, boolean status) {
        this.booking = booking;
        this.dose = dose;
        this.account = account;
        this.injectionDate = injectionDate;
        this.status = status;
    }
}
