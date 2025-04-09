package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.api;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.DiagnosisRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.VaccineInjectionRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination.DiagnosisService;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccination")
public class VaccinationController {

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private VaccinationService vaccinationService;
    

    // 1. Record doctor's diagnosis
    @PostMapping("/diagnosis/{bookingId}/{doctorId}")
    @SuppressWarnings("rawtypes")
    public ApiResponse recordDiagnosis(
            @PathVariable int bookingId,
            @PathVariable String doctorId,
            @RequestBody DiagnosisRequest request) {
        return diagnosisService.recordDiagnosisAndReleaseStaff(bookingId, doctorId, request);
    }

    // 2. Record vaccine injection
    @PostMapping("/inject/{bookingId}/{nurseId}")
    @SuppressWarnings("rawtypes")
    public ApiResponse recordVaccineInjection(
            @PathVariable int bookingId,
            @PathVariable String nurseId,
            @RequestBody VaccineInjectionRequest request) {
        return vaccinationService.recordVaccineInjectionAndReleaseStaff(bookingId, nurseId, request);
    }

    // 8. Create next vaccination schedule
    @PostMapping("/schedule/next/{bookingId}")
    @SuppressWarnings("rawtypes")
    public ApiResponse createNextSchedule(@PathVariable int bookingId) {
        return vaccinationService.createNextSchedule(bookingId);
    }

    /**
     * Cập nhật chẩn đoán và giải phóng nhân viên
     * 
     * @param diagnosisId ID của chẩn đoán
     * @param doctorId ID của bác sĩ thực hiện chẩn đoán
     * @param request Thông tin cập nhật chẩn đoán
     * @return Kết quả cập nhật
     */
    @PutMapping("/diagnosis/{diagnosisId}/{doctorId}/complete")
    @SuppressWarnings("rawtypes")
    public ApiResponse completeDiagnosis(
            @PathVariable("diagnosisId") int diagnosisId,
            @PathVariable("doctorId") String doctorId,
            @RequestBody DiagnosisRequest request) {
        return diagnosisService.recordDiagnosisAndReleaseStaff(diagnosisId, doctorId, request);
    }
}
