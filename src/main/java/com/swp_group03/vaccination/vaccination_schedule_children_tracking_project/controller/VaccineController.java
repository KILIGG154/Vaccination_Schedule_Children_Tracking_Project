package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.controller;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.VaccineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    private static final Logger log = LoggerFactory.getLogger(VaccineController.class);

    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/addVaccine")
    public ResponseEntity addVaccine(@RequestBody VaccineRequest request) {
        Vaccine newVaccine = vaccineService.addVaccine(request);
        return ResponseEntity.ok(newVaccine);
    }

    @GetMapping("/get")
    public ApiResponse<List<ResponseVaccine>> getVaccines(){
       ApiResponse<List<ResponseVaccine>> vaccines = new ApiResponse<>();
       vaccines.setCode(404);
       vaccines.setResult(vaccineService.getVaccines());
         return vaccines;
    }

    @GetMapping("/get/combo")
    public ApiResponse<List<ResponseVaccineCombo>> getVaccinesCombo(){
       ApiResponse<List<ResponseVaccineCombo>> vaccinesCombo = new ApiResponse<>();
       vaccinesCombo.setCode(403);
       vaccinesCombo.setResult(vaccineService.getVaccineCombos());
         return vaccinesCombo;
    }

    @GetMapping("/{vaccineName}")
    public List<Vaccine> searchByName(@PathVariable String vaccineName){
        return vaccineService.searchByName(vaccineName);
    }

    @PostMapping("/combo/add")
    public ResponseEntity addVaccineCombo(@RequestBody VaccineComboRequest request){
        return ResponseEntity.ok(vaccineService.addVaccineCombo(request));
    }

//    @PostMapping("/combo/detail/{vaccineId}/{comboId}")
//    public ResponseEntity addVaccineComboDetail(@RequestBody VaccineCombeDetailRequest request, @PathVariable int vaccineId, @PathVariable int comboId){
//        log.info("Adding combo detail for vaccine {} and combo {}", vaccineId, comboId);
//        return ResponseEntity.ok(vaccineService.addVaccineComboDetail(request, vaccineId, comboId));
//    }

    // @PostMapping("/addVaccineComboDetail")
    // public ResponseEntity<?> addVaccineComboDetail(@RequestBody VaccineCombeDetailRequest request) {
    //     System.out.println("Received request: " + request);
    //     return ResponseEntity.ok(vaccineService.addVaccineComboDetail(request));
    // }

    @PostMapping("/combo/detail/{comboId}/{vaccineId}")
public ResponseEntity addVaccineComboDetail(@RequestBody VaccineCombeDetailRequest request, 
                                           @PathVariable int vaccineId, 
                                           @PathVariable int comboId) {
    log.info("Adding combo detail for vaccine {} and combo {}", vaccineId, comboId);
    
    // Kiểm tra dữ liệu đầu vào
    if (vaccineId <= 0) {
        return ResponseEntity.badRequest().body("Invalid vaccine ID");
    }
    
    if (comboId <= 0) {
        return ResponseEntity.badRequest().body("Invalid combo ID");
    }
    
    if (request == null) {
        return ResponseEntity.badRequest().body("Request body cannot be null");
    }
    
    try {
        VaccineComboDetail result = vaccineService.addVaccineComboDetail(request, vaccineId, comboId);
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        log.error("Error adding vaccine combo detail", e);
        return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
    }
}
}

