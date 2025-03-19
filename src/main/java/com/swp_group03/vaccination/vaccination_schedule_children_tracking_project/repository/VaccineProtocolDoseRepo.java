package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineProtocolDoseRepo extends JpaRepository<VaccineProtocolDose, Long> {
    List<VaccineProtocolDose> findByVaccineId(int vaccineId);
}
