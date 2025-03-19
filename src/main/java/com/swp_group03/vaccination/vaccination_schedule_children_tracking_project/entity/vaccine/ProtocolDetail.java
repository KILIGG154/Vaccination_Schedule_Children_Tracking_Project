package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "protocol_detail")
public class ProtocolDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @Column(name = "dose_number")
    private int doseNumber;

    @Column(name = "interval_days")
    private int intervalDays;

    @OneToMany(mappedBy = "protocolDetail", fetch = FetchType.LAZY)
    private List<VaccineProtocolDose> vaccineProtocolDoses;

    public ProtocolDetail() {
    }

    public ProtocolDetail(Protocol protocol, int doseNumber, int intervalDays) {
        this.protocol = protocol;
        this.doseNumber = doseNumber;
        this.intervalDays = intervalDays;
    }

    public Long getDetailId() {
        return detailId;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public int getDoseNumber() {
        return doseNumber;
    }

    public void setDoseNumber(int doseNumber) {
        this.doseNumber = doseNumber;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }
    
    public List<VaccineProtocolDose> getVaccineProtocolDoses() {
        return vaccineProtocolDoses;
    }

    public void setVaccineProtocolDoses(List<VaccineProtocolDose> vaccineProtocolDoses) {
        this.vaccineProtocolDoses = vaccineProtocolDoses;
    }

    public void addVaccineProtocolDose(VaccineProtocolDose vaccineProtocolDose) {
        vaccineProtocolDoses.add(vaccineProtocolDose);
        vaccineProtocolDose.setProtocolDetail(this);
    }

    public void removeVaccineProtocolDose(VaccineProtocolDose vaccineProtocolDose) {
        vaccineProtocolDoses.remove(vaccineProtocolDose);
        vaccineProtocolDose.setProtocolDetail(null);
    }
    
}
