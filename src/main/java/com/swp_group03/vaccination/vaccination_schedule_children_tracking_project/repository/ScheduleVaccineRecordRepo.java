package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.ScheduleVaccineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleVaccineRecordRepo extends JpaRepository<ScheduleVaccineRecord, Long> {
    
    /**
     * Tìm bản ghi tiêm chủng theo ID của lịch hẹn
     * 
     * @param bookingId ID của lịch hẹn
     * @return Bản ghi tiêm chủng tương ứng (nếu có)
     */
    @Query("SELECT svr FROM ScheduleVaccineRecord svr WHERE svr.booking.bookingId = :bookingId")
    Optional<ScheduleVaccineRecord> findByBookingId(@Param("bookingId") int bookingId);

    /**
     * Tìm tất cả bản ghi tiêm chủng theo ID booking
     */
    List<ScheduleVaccineRecord> findByBookingBookingId(int bookingId);
}
