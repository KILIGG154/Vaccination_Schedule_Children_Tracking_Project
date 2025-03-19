package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.DiagnosisRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.DiagnosisRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DiagnosisService {

    @Autowired
    private DiagnosisRepo diagnosisRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo accountRepo;

    /**
     * Record a doctor's diagnosis for a child
     * @param bookingId The booking ID
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
            Diagnosis diagnosis = diagnosisRepo.findByBookingBookingId(bookingId).orElseThrow(()->
                    new AppException(ErrorCode.DIAGNOSIS_NOT_FOUND));
            if (diagnosis == null) {
                diagnosis = new Diagnosis();
                diagnosis.setBooking(booking);
                diagnosis.setAccount(doctor);
            }

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

} 