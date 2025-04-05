package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisRepo extends JpaRepository<Diagnosis, Integer> {
    
    /**
     * Tìm bản ghi chẩn đoán theo ID booking
     * 
     * @param bookingId ID của booking
     * @return Bản ghi chẩn đoán tương ứng (nếu có)
     */
    @Query("SELECT d FROM Diagnosis d WHERE d.booking.bookingId = :bookingId")
    Optional<Diagnosis> findByBookingId(@Param("bookingId") int bookingId);
    
    /**
     * Phương thức khác để tìm bản ghi chẩn đoán theo ID booking
     */
    Optional<Diagnosis> findByBookingBookingId(int bookingId);
}
