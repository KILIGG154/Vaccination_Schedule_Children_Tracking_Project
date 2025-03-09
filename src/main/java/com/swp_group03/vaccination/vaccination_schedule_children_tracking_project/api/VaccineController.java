package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Vaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCategory;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.VaccineComboDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCategoryRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineCombeDetailRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineComboRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.Vaccine.VaccineRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccine;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineCombo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Vaccine.ResponseVaccineDetails;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.VaccineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {
    private static final Logger log = LoggerFactory.getLogger(VaccineController.class);

    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/addCategory")
    public ApiResponse<VaccineCategory> addVaccineCategory(@RequestBody VaccineCategoryRequest request) {
        return ApiResponse.<VaccineCategory>builder()
                .code(200)
                .message("Successfully adding new Vaccine Category!")
                .result(vaccineService.addVaccineCategory(request))
                .build();
    }

    @PostMapping("/addVaccine/{categoryId}")
    public ApiResponse<Vaccine> addVaccine(@RequestBody VaccineRequest request, @PathVariable Long categoryId) {
//        vaccineService.addVaccine(request);
//        return ResponseEntity.ok("Vaccine added successfully: /n" + request);


        return  ApiResponse.<Vaccine>builder()
                .code(200)
                .message("Successfully adding new Vaccine!")
                .result(vaccineService.addVaccine(request, categoryId))
                .build();
    }


    @PostMapping("/combo/add")
    public ApiResponse<VaccineCombo> addVaccineCombo(@RequestBody VaccineComboRequest request){
//        return ResponseEntity.ok(vaccineService.addVaccineCombo(request));
        return  ApiResponse.<VaccineCombo>builder()
                .code(200)
                .message("Successfully adding new Vaccine Combo!")
                .result(vaccineService.addVaccineCombo(request))
                .build();
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
    public ApiResponse<VaccineComboDetail> addVaccineComboDetail(@RequestBody VaccineCombeDetailRequest request,
                                                @PathVariable int vaccineId,
                                                @PathVariable int comboId) {
        log.info("Adding combo detail for vaccine {} and combo {}", vaccineId, comboId);

        try {
            VaccineComboDetail result = vaccineService.addVaccineComboDetail(request, vaccineId, comboId);
//            return ResponseEntity.ok(result);
            return  ApiResponse.<VaccineComboDetail>builder()
                    .code(200)
                    .result(result)
                    .build();
        } catch (Exception e) {
            log.error("Error adding vaccine combo detail", e);
//            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
            return  ApiResponse.<VaccineComboDetail>builder()
                    .code(1008)
                    .message(ErrorCode.valueOf(e.getMessage()).getMessage())
                    .build();
        }
    }

//    @GetMapping("/{comboId}/totalPrice")
//    public double getTotalPrice(@PathVariable int comboId){
//        return vaccineService.getTotalPriceCombo(comboId);
//    }

    @GetMapping("/get")
    public ApiResponse<List<ResponseVaccine>> getAllVaccines(){
//        ApiResponse<List<ResponseVaccine>> vaccines = new ApiResponse<>();
//        vaccines.setCode(404);
//        vaccines.setResult(vaccineService.getVaccines());
//        return vaccines;

        return  ApiResponse.<List<ResponseVaccine>>builder()
                .code(200)
                .message("Successfully getting All Vaccines!")
                .result(vaccineService.getAllVaccines())
                .build();
    }

    @GetMapping("/get/combo")
    public ApiResponse<List<ResponseVaccineCombo>> getVaccinesCombo(){
        return  ApiResponse.<List<ResponseVaccineCombo>>builder()
                .code(200)
                .message("Successfully getting All Vaccine Combos!")
                .result(vaccineService.getVaccineCombos())
                .build();
    }

    @GetMapping("/get/comboDetail")
    public ApiResponse<List<ResponseVaccineDetails>> getAllVaccinesComboDetails(){

        return  ApiResponse.<List<ResponseVaccineDetails>>builder()
                .code(200)
                .message("Successfully getting All Vaccine Combo Details!")
                .result(vaccineService.getAllVaccineCombosDetails())
                .build();
    }

    @GetMapping("/{vaccineName}")
    public ApiResponse<List<ResponseVaccine>> searchByName(@PathVariable String vaccineName){
//        ApiResponse<List<ResponseVaccine>> response = new ApiResponse<>();
//        response.setCode(3000);
//        response.setResult(vaccineService.searchByName(vaccineName));
//        return response;

        return  ApiResponse.<List<ResponseVaccine>>builder()
                .code(200)
                .result(vaccineService.searchByName(vaccineName))
                .build();
        // return vaccineService.searchByName(vaccineName);
    }
}
