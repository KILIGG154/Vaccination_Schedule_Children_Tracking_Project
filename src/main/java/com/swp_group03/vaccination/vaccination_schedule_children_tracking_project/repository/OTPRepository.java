// OTPRepository.java
package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity, String> {
    
    @Query("SELECT o FROM OTPEntity o WHERE o.email = ?1 AND o.isUsed = false ORDER BY o.createdAt DESC")
    Optional<OTPEntity> findLatestValidOTPByEmail(String email);
    
    Optional<OTPEntity> findByEmailAndOtpAndIsUsedFalse(String email, String otp);
}