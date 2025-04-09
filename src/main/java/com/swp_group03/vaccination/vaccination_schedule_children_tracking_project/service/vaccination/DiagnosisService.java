package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.DiagnosisRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.DiagnosisRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking.StaffAssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DiagnosisService {

    @Autowired
    private DiagnosisRepo diagnosisRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo accountRepo;
    
    @Autowired
    private StaffAssignmentService staffAssignmentService;

    /**
     * Record a doctor's diagnosis for a child
     * @param bookingId The booking ID
     * @param doctorId The doctor ID
     * @param request The diagnosis details
     * @return ApiResponse with result
     */
    @SuppressWarnings("rawtypes")
    public ApiResponse recordDiagnosis(int bookingId, String doctorId, DiagnosisRequest request) {
        try {
            // Validate booking exists
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // Validate booking status
            if (booking.getStatus() != BookingStatus.ASSIGNED) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in ASSIGNED status to record diagnosis")
                        .build();
            }

            // Validate doctor exists
            Account doctor = accountRepo.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // Create or update diagnosis
            Diagnosis diagnosis = diagnosisRepo.findByBookingBookingId(bookingId)
                    .orElse(new Diagnosis());
            
            diagnosis.setBooking(booking);
            diagnosis.setAccount(doctor);
            diagnosis.setDescription(request.getDescription());
            diagnosis.setTreatment(request.getTreatment());
            diagnosis.setResult(request.getResult());

            diagnosisRepo.save(diagnosis);

            // Update booking status
            booking.setStatus(BookingStatus.DIAGNOSED);
            bookingRepo.save(booking);

            return ApiResponse.builder()
                    .code(200)
                    .message("Diagnosis recorded successfully")
                    .result(diagnosis)
                    .build();
        } catch (Exception e) {
            log.error("Error recording diagnosis: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Error recording diagnosis: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Ghi nhận chẩn đoán và giải phóng nhân viên
     *
     * @param bookingId ID của booking
     * @param doctorId ID của bác sĩ
     * @param request Thông tin chẩn đoán
     * @return ApiResponse
     */
    @Transactional
    @SuppressWarnings("rawtypes")
    public ApiResponse recordDiagnosisAndReleaseStaff(int bookingId, String doctorId, DiagnosisRequest request) {
        try {
            // Ghi nhận chẩn đoán và cập nhật trạng thái booking
            ApiResponse diagnosisResponse = recordDiagnosis(bookingId, doctorId, request);
            
            if (diagnosisResponse.getCode() != 200) {
                return diagnosisResponse;
            }
            
            // Giải phóng nhân viên (bác sĩ) sau khi hoàn thành chẩn đoán
            try {
                staffAssignmentService.releaseStaffFromBooking(bookingId);
                log.info("Released doctor after diagnosis for booking: {}", bookingId);
            } catch (Exception e) {
                // Log lỗi nhưng vẫn trả về thành công vì chẩn đoán đã được ghi nhận
                log.warn("Could not release doctor after diagnosis: {}", e.getMessage(), e);
            }
            
            return diagnosisResponse;
        } catch (Exception e) {
            log.error("Error recording diagnosis and releasing staff: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Lỗi khi ghi nhận chẩn đoán: " + e.getMessage())
                    .build();
        }
    }
} 