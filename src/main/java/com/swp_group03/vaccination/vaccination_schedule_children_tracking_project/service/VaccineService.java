package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepo vaccineRepo;

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
}
