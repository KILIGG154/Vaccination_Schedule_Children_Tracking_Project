package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineOrderDetailId extends JpaRepository<VaccineOrderDetail, VaccineOrderDetailId> {
}
