package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ScheduleVaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleVaccineRecordRepo extends JpaRepository<ScheduleVaccineRecord, Long> {
    List<ScheduleVaccineRecord> findByBookingBookingId(int bookingId);
}
