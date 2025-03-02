package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineComboDetailRepo extends JpaRepository<VaccineComboDetail, VaccineComboDetailId> {
}
