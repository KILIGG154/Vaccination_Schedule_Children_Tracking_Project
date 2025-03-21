package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.vaccine.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccine.VaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponse<VaccineCategory>> addCategory(@RequestBody @Valid VaccineCategoryRequest request) {
        VaccineCategory category = vaccineService.addVaccineCategory(request);
        ApiResponse<VaccineCategory> response = ApiResponse.<VaccineCategory>builder()
                .code(201)
                .message("Category created successfully")
                .result(category)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<VaccineCategory>>> getAllCategories() {
        List<VaccineCategory> categories = vaccineService.getAllCategories();
        ApiResponse<List<VaccineCategory>> response = ApiResponse.<List<VaccineCategory>>builder()
                .code(200)
                .message("Categories retrieved successfully")
                .result(categories)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Vaccine>> addVaccine(@RequestBody @Valid VaccineRequest request, @PathVariable("categoryId") Long categoryId) {
        Vaccine vaccine = vaccineService.addVaccine(request, categoryId);
        ApiResponse<Vaccine> response = ApiResponse.<Vaccine>builder()
                .code(201)
                .message("Vaccine created successfully")
                .result(vaccine)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<ResponseVaccine>>> getAllVaccines() {
        List<ResponseVaccine> vaccines = vaccineService.getAllVaccines();
        ApiResponse<List<ResponseVaccine>> response = ApiResponse.<List<ResponseVaccine>>builder()
                .code(200)
                .message("Vaccines retrieved successfully")
                .result(vaccines)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addCombo")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<VaccineCombo>> addVaccineCombo(@RequestBody @Valid VaccineComboRequest request) {
        VaccineCombo combo = vaccineService.addVaccineCombo(request);
        ApiResponse<VaccineCombo> response = ApiResponse.<VaccineCombo>builder()
                .code(201)
                .message("Vaccine combo created successfully")
                .result(combo)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/addDetailCombo/{vaccineId}/{comboId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<VaccineComboDetail>> addVaccineComboDetail(
            @RequestBody @Valid VaccineComboDetailRequest request,
            @PathVariable("vaccineId") Integer vaccineId,
            @PathVariable("comboId") Integer comboId) {
        
        VaccineComboDetail detail = vaccineService.addVaccineComboDetail(request, vaccineId, comboId);
        ApiResponse<VaccineComboDetail> response = ApiResponse.<VaccineComboDetail>builder()
                .code(201)
                .message("Vaccine combo detail created successfully")
                .result(detail)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/combo")
    public ResponseEntity<ApiResponse<List<ResponseVaccineCombo>>> getVaccineCombos() {
        List<ResponseVaccineCombo> combos = vaccineService.getVaccineCombos();
        ApiResponse<List<ResponseVaccineCombo>> response = ApiResponse.<List<ResponseVaccineCombo>>builder()
                .code(200)
                .message("Vaccine combos retrieved successfully")
                .result(combos)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comboDetails")
    public ResponseEntity<ApiResponse<List<ResponseVaccineDetails>>> getAllVaccineCombosDetails() {
        List<ResponseVaccineDetails> details = vaccineService.getAllVaccineCombosDetails();
        ApiResponse<List<ResponseVaccineDetails>> response = ApiResponse.<List<ResponseVaccineDetails>>builder()
                .code(200)
                .message("Vaccine combo details retrieved successfully")
                .result(details)
                .build();
        return ResponseEntity.ok(response);
    }

    // Protocol API endpoints
    @PostMapping("/protocol/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProtocolResponse>> addProtocol(@RequestBody @Valid ProtocolRequest request) {
        ProtocolResponse protocol = vaccineService.addNewProtocol(request);
        ApiResponse<ProtocolResponse> response = ApiResponse.<ProtocolResponse>builder()
                .code(201)
                .message("Protocol created successfully")
                .result(protocol)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/protocols")
    public ResponseEntity<ApiResponse<List<ProtocolResponse>>> getAllProtocols() {
        List<ProtocolResponse> protocols = vaccineService.getAllProtocols();
        ApiResponse<List<ProtocolResponse>> response = ApiResponse.<List<ProtocolResponse>>builder()
                .code(200)
                .message("Protocols retrieved successfully")
                .result(protocols)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protocol/{protocolId}")
    public ResponseEntity<ApiResponse<ProtocolResponse>> getProtocolById(@PathVariable Long protocolId) {
        ProtocolResponse protocol = vaccineService.getProtocolById(protocolId);
        ApiResponse<ProtocolResponse> response = ApiResponse.<ProtocolResponse>builder()
                .code(200)
                .message("Protocol retrieved successfully")
                .result(protocol)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/protocol/{protocolId}/addVaccine/{vaccineId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<VaccineProtocolDose>>> addVaccineToProtocol(
            @PathVariable Integer vaccineId,
            @PathVariable Long protocolId) {
        List<VaccineProtocolDose> doses = vaccineService.addVaccineToProtocol(vaccineId, protocolId);
        ApiResponse<List<VaccineProtocolDose>> response = ApiResponse.<List<VaccineProtocolDose>>builder()
                .code(201)
                .message("Vaccine added to protocol successfully")
                .result(doses)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/protocol/dose/{vaccineId}")
    public ResponseEntity<ApiResponse<ResponseProtocolDose>> getProtocolDoseByVaccine(@PathVariable Integer vaccineId) {
        ResponseProtocolDose doses = vaccineService.getProtocolDoseByVaccine(vaccineId);
        ApiResponse<ResponseProtocolDose> response = ApiResponse.<ResponseProtocolDose>builder()
                .code(200)
                .message("Protocol doses retrieved successfully")
                .result(doses)
                .build();
        return ResponseEntity.ok(response);
    }
}