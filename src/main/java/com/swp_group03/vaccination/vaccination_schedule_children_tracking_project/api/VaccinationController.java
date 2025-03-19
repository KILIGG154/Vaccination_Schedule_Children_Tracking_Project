package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.DiagnosisRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.VaccineInjectionRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination.DiagnosisService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private VaccinationService vaccinationService;

    // 5. Record diagnosis
    @PostMapping("/{bookingId}/diagnosis/{doctorId}")
    public ApiResponse recordDiagnosis(
            @PathVariable int bookingId,
            @PathVariable String doctorId,
            @RequestBody DiagnosisRequest request) {
        return diagnosisService.recordDiagnosis(bookingId, doctorId, request);
    }

    // 6. Record vaccine injection
    @PostMapping("/{bookingId}/injection/{nurseId}")
    public ApiResponse recordVaccineInjection(
            @PathVariable int bookingId,
            @PathVariable String nurseId,
            @RequestBody VaccineInjectionRequest request) {
        return vaccinationService.recordVaccineInjection(bookingId, nurseId, request);
    }

    // 8. Create next vaccination schedule
    @PostMapping("/{bookingId}/next-schedule")
    public ApiResponse createNextSchedule(
            @PathVariable int bookingId) {
        return vaccinationService.createNextSchedule(bookingId);
    }
}
