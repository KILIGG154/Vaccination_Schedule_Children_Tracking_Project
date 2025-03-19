package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ScheduleVaccineRecord;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineTherapyRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vaccine_protocol_dose")
public class VaccineProtocolDose {

    @Id
    @Column(name = "dose_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_detail_id")
    private ProtocolDetail protocolDetail;

    @OneToMany(mappedBy = "dose", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VaccineTherapyRecord> therapyList = new ArrayList<>();


    @OneToMany(mappedBy = "dose", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ScheduleVaccineRecord> recordList = new ArrayList<>();


    public VaccineProtocolDose() {
    }

    public void addTherapy(VaccineTherapyRecord therapy) {
        therapyList.add(therapy);
        therapy.setDose(this);
    }

    public void removeTherapy(VaccineTherapyRecord therapy) {
        therapyList.remove(therapy);
        therapy.setDose(null);
    }

    public void addRecord(ScheduleVaccineRecord record) {
        recordList.add(record);
        record.setDose(this);
    }

    public void removeRecord(ScheduleVaccineRecord record) {
        recordList.remove(record);
        record.setDose(null);
    }
}
