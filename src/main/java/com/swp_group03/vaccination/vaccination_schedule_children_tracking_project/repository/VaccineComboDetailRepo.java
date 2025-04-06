package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineComboDetailRepo extends JpaRepository<VaccineComboDetail, Integer> {
    
    // Find by combo id
    List<VaccineComboDetail> findByComboId(int comboId);
    
    // Find by vaccine id
    List<VaccineComboDetail> findByVaccineId(int vaccineId);
    
    // Find by both vaccine id and combo id
    @Query("SELECT vcd FROM VaccineComboDetail vcd WHERE vcd.vaccineId = :vaccineId AND vcd.comboId = :comboId")
    List<VaccineComboDetail> findByVaccineIdAndComboId(
        @Param("vaccineId") int vaccineId, 
        @Param("comboId") int comboId
    );
    
    // Delete by vaccine id and combo id
    void deleteByVaccineIdAndComboId(int vaccineId, int comboId);
}
