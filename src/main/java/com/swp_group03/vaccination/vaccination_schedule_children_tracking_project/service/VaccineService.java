package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper.VaccicneMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class VaccineService {

    @Autowired
    private VaccineRepo vaccineRepo;

    @Autowired
    private VaccineComboRepo  vaccineCombos;

    @Autowired
    private VaccineComboDetailRepo vaccineComboDetail;

    @Autowired
    private VaccicneMapper vaccicneMapper;

    @Transactional
    public Vaccine addVaccine(VaccineRequest request){
        Vaccine vaccine = new Vaccine();
        vaccine.setName(request.getName());
        vaccine.setDescription(request.getDescription());
        vaccine.setManufacturer(request.getManufacturer());
        vaccine.setDosage(request.getDosage());
        vaccine.setContraindications(request.getContraindications());
        vaccine.setPrecautions(request.getPrecautions());
        vaccine.setInteractions(request.getInteractions());
        vaccine.setAdverseReactions(request.getAdverseReactions());
        vaccine.setStorageConditions(request.getStorageConditions());
        vaccine.setRecommended(request.getRecommended());
        vaccine.setPreVaccination(request.getPreVaccination());
        vaccine.setCompatibility(request.getCompatibility());
        vaccine.setQuantity(request.getQuantity());
        vaccine.setExpirationDate(request.getExpirationDate());
        vaccine.setImagineUrl(request.getImagineUrl());
        vaccine.setStatus("true");


        return vaccineRepo.save(vaccine);
    }

    public List<ResponseVaccine> getVaccines(){
        List<ResponseVaccine> vaccines =  vaccicneMapper.toResponseVaccine(vaccineRepo.findAll());
        return vaccines;
    }

    public List<Vaccine> searchByName(String vaccineName){
        return vaccineRepo.findByNameContainingIgnoreCase(vaccineName);
    }

    @Transactional
    public VaccineCombo addVaccineCombo(VaccineComboRequest request){
        VaccineCombo vaccineCombo = new VaccineCombo();
        vaccineCombo.setComboName(request.getComboName());
        vaccineCombo.setDescription(request.getDescription());
        vaccineCombo.setStatus(true);
        return vaccineCombos.save(vaccineCombo);
    }

    @Transactional
    public VaccineComboDetail addVaccineComboDetail(VaccineCombeDetailRequest request, int vaccineId, int comboId){
        // Log để debug
        log.info("Adding VaccineComboDetail for vaccineId={}, comboId={}");
        
        // Tìm Vaccine và VaccineCombo theo ID
        Vaccine vaccine = vaccineRepo.findById(vaccineId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id: " ));

        VaccineCombo vaccineCombo = vaccineCombos.findById(comboId)
                .orElseThrow(() -> new RuntimeException("Vaccine Combo not found with id: "));

        // Tạo ID cho VaccineComboDetail
        VaccineComboDetailId key = new VaccineComboDetailId();
        key.setVaccineId(request.getVaccineId());
        key.setComboId(request.getComboId());
        
        log.info("Created composite key: vaccineId={}, comboId={}", key.getVaccineId(), key.getComboId());


         // Tạo và cấu hình VaccineComboDetail - THAY ĐỔI Ở ĐÂY
    VaccineComboDetail detail = new VaccineComboDetail();
    detail.setVaccineId(vaccineId);  // Đặt ID trực tiếp thay vì thông qua đối tượng key
    detail.setComboId(comboId);      // Đặt ID trực tiếp thay vì thông qua đối tượng key
    detail.setVaccine(vaccine);
    detail.setCombo(vaccineCombo);
    detail.setDose(request.getDose());
    detail.setAgeGroup(request.getAgeGroup());
    detail.setSaleOff(request.getSaleOff());
    
    log.info("Saving VaccineComboDetail with vaccineId={}, comboId={}", detail.getVaccineId(), detail.getComboId());

    // Lưu và trả về kết quả
    return vaccineComboDetail.save(detail);
}
//         // Tạo và cấu hình VaccineComboDetail
//         VaccineComboDetail detail = new VaccineComboDetail();
//         detail.setId(key);
//         detail.setVaccine(vaccine);
//         detail.setCombo(vaccineCombo);
//         detail.setDose(request.getDose());
//         detail.setAgeGroup(request.getAgeGroup());
//         detail.setSaleOff(request.getSaleOff());

// //        vaccine.getVaccineComboDetails().add(detail);
// //        vaccineCombo.getVaccineComboDetails().add(detail);
// //        log.info("Added VaccineComboDetail to Vaccine and VaccineCombo", detail.getVaccine(), detail.getCombo());


//         // Lưu và trả về kết quả
// //        vaccineRepo.save(vaccine);
// //        vaccineCombos.save(vaccineCombo);
//         return vaccineComboDetail.saveAndFlush(detail);
//     }

    public List<ResponseVaccineCombo> getVaccineCombos(){
        return vaccicneMapper.toResponseVaccineCombo(vaccineCombos.findAll());
    }


}
