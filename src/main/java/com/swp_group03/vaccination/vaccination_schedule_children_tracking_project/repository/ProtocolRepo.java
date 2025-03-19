package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocolRepo extends JpaRepository<Protocol, Long> {
}
