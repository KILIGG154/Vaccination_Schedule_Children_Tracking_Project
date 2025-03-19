package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.Protocol;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.ProtocolDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProtocolDetailRepo extends JpaRepository<ProtocolDetail, Long> {
    List<ProtocolDetail> findByProtocolOrderByDoseNumber(Protocol protocol);
}
