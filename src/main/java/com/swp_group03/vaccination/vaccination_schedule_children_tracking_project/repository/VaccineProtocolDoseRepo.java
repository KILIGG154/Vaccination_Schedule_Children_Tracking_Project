package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineProtocolDoseRepo extends JpaRepository<VaccineProtocolDose, Long> {
    
    /**
     * Tìm liều vaccine đầu tiên cho một booking
     * 
     * @param bookingId ID của booking
     * @return Liều vaccine đầu tiên (nếu có)
     */
    @Query("SELECT vpd FROM VaccineProtocolDose vpd " +
           "JOIN vpd.recordList r " +
           "WHERE r.booking.bookingId = :bookingId " +
           "ORDER BY vpd.doseId ASC")
    List<VaccineProtocolDose> findByBookingIdOrderByDoseId(@Param("bookingId") int bookingId);
    
    /**
     * Tìm liều vaccine đầu tiên cho một booking
     */
    default Optional<VaccineProtocolDose> findFirstByBookingId(int bookingId) {
        List<VaccineProtocolDose> doses = findByBookingIdOrderByDoseId(bookingId);
        return doses.isEmpty() ? Optional.empty() : Optional.of(doses.get(0));
    }
    
    /**
     * Tìm liều vaccine cho một booking (phương thức đơn giản, trả về liều đầu tiên)
     */
    default Optional<VaccineProtocolDose> findByBookingId(int bookingId) {
        return findFirstByBookingId(bookingId);
    }
    
    /**
     * Tìm tất cả các liều vaccine cho một booking
     * 
     * @param bookingId ID của booking
     * @return Danh sách liều vaccine
     */
    @Query("SELECT vpd FROM VaccineProtocolDose vpd " +
           "JOIN vpd.recordList r " +
           "WHERE r.booking.bookingId = :bookingId")
    List<VaccineProtocolDose> findAllByBookingId(@Param("bookingId") int bookingId);
    
    /**
     * Tìm tất cả các liều vaccine theo vaccineId
     * 
     * @param vaccineId ID của vaccine
     * @return Danh sách liều vaccine
     */
    @Query("SELECT vpd FROM VaccineProtocolDose vpd " +
           "WHERE vpd.vaccine.id = :vaccineId")
    List<VaccineProtocolDose> findByVaccineId(@Param("vaccineId") int vaccineId);
    
    /**
     * Tìm tất cả các liều vaccine theo vaccineId (phiên bản sử dụng Integer)
     */
    default List<VaccineProtocolDose> findByVaccineId(Integer vaccineId) {
        return findByVaccineId(vaccineId.intValue());
    }
}
