package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineTherapyRecord;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineTherapyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface VaccineTherapyRecordRepo extends JpaRepository<VaccineTherapyRecord, Long> {
    List<VaccineTherapyRecord> findByBookingBookingId(int bookingId);
    List<VaccineTherapyRecord> findByTherapyDateAndStatus(Date therapyDate, VaccineTherapyStatus status);
}
