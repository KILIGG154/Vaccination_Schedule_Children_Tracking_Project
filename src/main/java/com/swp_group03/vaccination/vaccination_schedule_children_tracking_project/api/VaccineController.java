package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccine.VaccineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;


    //Vaccine Category API endpoints
    @PostMapping("/category/add")
    public  ApiResponse<VaccineCategory> addCategory(@RequestBody @Valid VaccineCategoryRequest request) {
        VaccineCategory category = vaccineService.addVaccineCategory(request);
        return ApiResponse.<VaccineCategory>builder()
                .code(201)
                .message("Category created successfully")
                .result(category)
                .build();
    }

    @GetMapping("/categories")
    public  ApiResponse<List<VaccineCategory>> getAllCategories() {
        List<VaccineCategory> categories = vaccineService.getAllCategories();
        return ApiResponse.<List<VaccineCategory>>builder()
                .code(200)
                .message("Categories retrieved successfully")
                .result(categories)
                .build();
    }



    // Protocol API endpoints
    @PostMapping("/protocol/add")
    public  ApiResponse<ProtocolResponse> addProtocol(@RequestBody @Valid ProtocolRequest request) {
        ProtocolResponse protocol = vaccineService.addNewProtocol(request);
        return ApiResponse.<ProtocolResponse>builder()
                .code(201)
                .message("Protocol created successfully")
                .result(protocol)
                .build();
    }

    @GetMapping("/protocols")
    public  ApiResponse<List<ProtocolResponse>> getAllProtocols() {
        List<ProtocolResponse> protocols = vaccineService.getAllProtocols();
        return ApiResponse.<List<ProtocolResponse>>builder()
                .code(200)
                .message("Protocols retrieved successfully")
                .result(protocols)
                .build();
    }

    @GetMapping("/protocol/{protocolId}")
    public  ApiResponse<ProtocolResponse> getProtocolById(@PathVariable Long protocolId) {
        ProtocolResponse protocol = vaccineService.getProtocolById(protocolId);
        return ApiResponse.<ProtocolResponse>builder()
                .code(200)
                .message("Protocol retrieved successfully")
                .result(protocol)
                .build();
    }



    // Vaccine Protocol Dose API endpoints
    @PostMapping("/protocol/{protocolId}/addVaccine/{vaccineId}")
    public ApiResponse<List<VaccineProtocolDose>> addVaccineToProtocol(
            @PathVariable Integer vaccineId,
            @PathVariable Long protocolId) {
        List<VaccineProtocolDose> doses = vaccineService.addVaccineToProtocol(vaccineId, protocolId);
        return ApiResponse.<List<VaccineProtocolDose>>builder()
                .code(201)
                .message("Vaccine added to protocol successfully")
                .result(doses)
                .build();    }

    @GetMapping("/protocol/dose/{vaccineId}")
    public ApiResponse<ResponseProtocolDose> getProtocolDoseByVaccine(@PathVariable Integer vaccineId) {
        ResponseProtocolDose doses = vaccineService.getProtocolDoseByVaccine(vaccineId);
        return ApiResponse.<ResponseProtocolDose>builder()
                .code(200)
                .message("Protocol doses retrieved successfully")
                .result(doses)
                .build();
    }



    // Vaccine API endpoints
    @PostMapping("/add/{categoryId}")
    public  ApiResponse<Vaccine> addVaccine(@RequestBody @Valid VaccineRequest request, @PathVariable("categoryId") Long categoryId) {
        Vaccine vaccine = vaccineService.addVaccine(request, categoryId);
        return ApiResponse.<Vaccine>builder()
                .code(201)
                .message("Vaccine created successfully")
                .result(vaccine)
                .build();
    }

    @GetMapping("/get")
    public  ApiResponse<List<ResponseVaccine>> getAllVaccines() {
        List<ResponseVaccine> vaccines = vaccineService.getAllVaccines();
        return ApiResponse.<List<ResponseVaccine>>builder()
                .code(200)
                .message("Vaccines retrieved successfully")
                .result(vaccines)
                .build();

    }

    @GetMapping("/get/{id}")
    public  ApiResponse<ResponseVaccine> getVaccineById(@PathVariable int id) {
        ResponseVaccine vaccine = vaccineService.getVaccineById(id);
        return ApiResponse.<ResponseVaccine>builder()
                .code(200)
                .message("Vaccine retrieved successfully")
                .result(vaccine)
                .build();
    }
    @PutMapping("/{vaccID}/active")
    public ApiResponse activeVaccine(@PathVariable int vaccID) {
        Vaccine vaccine = vaccineService.activeVaccie(vaccID);
       return ApiResponse.builder()
                .code(200)
                .result(vaccine)
                .build();

    }

    @PutMapping("/{vaccineID}/deactive-vaccine")
    public ApiResponse deActiveVaccine(@PathVariable int vaccineID) {
        Vaccine vaccine = vaccineService.deactiveVaccine(vaccineID);
      return  ApiResponse.builder()
                .code(200)
                .message("Vaccine activated successfully")
                .result(vaccine)
                .build();
    }


    // Vaccine Combo API endpoints
    @PostMapping("/addCombo")
    public  ApiResponse<VaccineCombo> addVaccineCombo(@RequestBody @Valid VaccineComboRequest request) {
        VaccineCombo combo = vaccineService.addVaccineCombo(request);
        return ApiResponse.<VaccineCombo>builder()
                .code(201)
                .message("Vaccine combo created successfully")
                .result(combo)
                .build();
    }

    @GetMapping("/combo")
    public  ApiResponse<List<ResponseVaccineCombo>> getVaccineCombos() {
        List<ResponseVaccineCombo> combos = vaccineService.getVaccineCombos();
        return ApiResponse.<List<ResponseVaccineCombo>>builder()
                .code(200)
                .message("Vaccine combos retrieved successfully")
                .result(combos)
                .build();
    }

    @GetMapping("/combo/{id}")
    public  ApiResponse<ResponseVaccineCombo> getVaccineComboById(@PathVariable int id) {
        ResponseVaccineCombo combo = vaccineService.getVaccineComboById(id);
        return ApiResponse.<ResponseVaccineCombo>builder()
                .code(200)
                .message("Vaccine combo retrieved successfully")
                .result(combo)
                .build();
    }

    @PutMapping("/{comboID}/deactive-combo")
    public ApiResponse<VaccineCombo> deActiveCombo(@PathVariable int comboID) {
        VaccineCombo vaccineCombo = vaccineService.deactiveCombo(comboID);
            return ApiResponse.<VaccineCombo>builder()
                .code(200)
                .message("Vaccine activated successfully")
                .result(vaccineCombo)
                .build();

    }

    @PutMapping("/{comboID}/active-combo")
    public ApiResponse<VaccineCombo> activeCombo(@PathVariable int comboID) {
        VaccineCombo vaccineCombo = vaccineService.activeCombo(comboID);
        return ApiResponse.<VaccineCombo>builder()
                .code(200)
                .message("Vaccine activated successfully")
                .result(vaccineCombo)
                .build();
    }


    // Vaccine Combo Detail API endpoints
    @PostMapping("/addDetailCombo/{vaccineId}/{comboId}")
    public  ApiResponse<VaccineComboDetail> addVaccineComboDetail(
            @RequestBody @Valid VaccineComboDetailRequest request,
            @PathVariable("vaccineId") Integer vaccineId,
            @PathVariable("comboId") Integer comboId) {
        
        VaccineComboDetail detail = vaccineService.addVaccineComboDetail(request, vaccineId, comboId);
        return  ApiResponse.<VaccineComboDetail>builder()
                .code(201)
                .message("Vaccine combo detail created successfully")
                .result(detail)
                .build();
    }

    @GetMapping("/comboDetails")
    public  ApiResponse<List<ResponseVaccineDetails>> getAllVaccineCombosDetails() {
        List<ResponseVaccineDetails> details = vaccineService.getAllVaccineCombosDetails();
        return ApiResponse.<List<ResponseVaccineDetails>>builder()
                .code(200)
                .message("Vaccine combo details retrieved successfully")
                .result(details)
                .build();
    }


}