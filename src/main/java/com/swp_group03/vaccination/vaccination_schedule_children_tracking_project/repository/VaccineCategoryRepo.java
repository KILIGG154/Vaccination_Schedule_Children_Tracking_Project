package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineCategoryRepo extends JpaRepository<VaccineCategory, Long> {

}
