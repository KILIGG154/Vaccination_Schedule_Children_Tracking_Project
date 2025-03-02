package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepo vaccineRepo;

    @Autowired
    private VaccineComboRepo  vaccineCombos;

    @Autowired
    private VaccineComboDetailRepo vaccineComboDetail;

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

    public List<Vaccine> getVaccines(){
        return vaccineRepo.findAll();
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
        Vaccine vaccine = vaccineRepo.findById(vaccineId).orElseThrow(() -> new RuntimeException("Vaccine not found"));
        VaccineCombo vaccineCombo = vaccineCombos.findById(comboId).orElseThrow(() -> new RuntimeException("Vaccine Combo not found"));

        VaccineComboDetailId key = new VaccineComboDetailId(vaccineId, comboId);
        VaccineComboDetail vaccineComboDetail1 = new VaccineComboDetail();
        vaccineComboDetail1.setId(key);
        vaccineComboDetail1.setVaccine(vaccine);
        vaccineComboDetail1.setCombo(vaccineCombo);
        vaccineComboDetail1.setDose(request.getDose());
        vaccineComboDetail1.setAgeGroup(request.getAgeGroup());
        vaccineComboDetail1.setSaleOff(request.getSaleOff());
        return vaccineComboDetail.save(vaccineComboDetail1);
    }


}
