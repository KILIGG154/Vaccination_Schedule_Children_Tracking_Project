package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Protocol")
public class Protocol {

    @Id
    @Column(name = "protocol_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long protocolId;

    @Column(name = "Name", unique = true)
    @Nationalized
    private String name;

    @Column(name = "Description")
    @Nationalized
    private String description;

    @OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProtocolDetail> details = new ArrayList<>();


    public  Protocol() {
    }
    public Protocol(String name, String description) {}


    // Phương thức tiện ích để thêm chi tiết
    public void addDetail(ProtocolDetail detail) {
        details.add(detail);
        detail.setProtocol(this);
    }

    public void removeDetail(ProtocolDetail detail) {
        details.remove(detail);
        detail.setProtocol(null);
    }
}
