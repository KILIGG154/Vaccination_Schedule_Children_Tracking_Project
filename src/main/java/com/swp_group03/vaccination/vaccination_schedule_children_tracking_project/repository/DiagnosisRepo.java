package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisRepo extends JpaRepository<Diagnosis, Long> {
    Optional<Diagnosis> findByBookingBookingId(int bookingId);
}
