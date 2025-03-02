package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/addVaccine")
    public ResponseEntity addVaccine(@RequestBody VaccineRequest request) {
        Vaccine newVaccine = vaccineService.addVaccine(request);
        return ResponseEntity.ok(newVaccine);
    }

    @GetMapping("/get")
    public List<Vaccine> getVaccines(){
        return vaccineService.getVaccines();
    }

    @GetMapping("/{vaccineName}")
    public List<Vaccine> searchByName(@PathVariable String vaccineName){
        return vaccineService.searchByName(vaccineName);
    }

    @PostMapping("/combo/add")
    public ResponseEntity addVaccineCombo(@RequestBody VaccineComboRequest request){
        return ResponseEntity.ok(vaccineService.addVaccineCombo(request));
    }

    @PostMapping("/combo/detail/{vaccineId}/{comboId}")
    public ResponseEntity addVaccineComboDetail(VaccineCombeDetailRequest request, @PathVariable int vaccineId, @PathVariable int comboId){
        return ResponseEntity.ok(vaccineService.addVaccineComboDetail(request,vaccineId, comboId));
    }
}
