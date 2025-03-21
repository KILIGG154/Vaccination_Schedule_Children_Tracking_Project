package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.booking.BookingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.booking.BookingResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.order.VaccineOrderDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ChildRepo childRepo;

    @Autowired
    private WorkingDateRepo workingDateRepo;

    @Autowired
    private WorkingScheduleRepo workingScheduleRepo;

    @Autowired
    private DiagnosisRepo diagnosisRepo;


//    public Booking createBookingRepo(int childID, BookingRequest bookingRequest) {
//        Child child = childRepo.findById(childID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        Booking booking = new Booking();
//        booking.setAppointmentDate(bookingRequest.getAppointmentDate());
//        booking.setStatus(booking.isStatus());
//        booking.setChild(child);
//        childRepo.save(child);
//        return bookingRepo.save(booking);
//    }

@Transactional
    public BookingDTO createBookingRepo(int childID, BookingRequest bookingRequest) {
        Child child = childRepo.findById(childID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Booking booking = new Booking();
        booking.setAppointmentDate(bookingRequest.getAppointmentDate());
        booking.setStatus(bookingRequest.getStatus() != null ? bookingRequest.getStatus() : BookingStatus.PENDING);
        booking.setChild(child);
        childRepo.save(child);
        bookingRepo.save(booking);
        return new BookingDTO(booking);
    }

    public BookingDTO getAllBooking(int bookingID){
        Booking booking = bookingRepo.findById(bookingID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new BookingDTO(booking);
    }

    public List<BookingResponse> getBook() {
        List<Booking> bookings = bookingRepo.findAll();
        return bookings.stream()
            .map(booking -> {
                try {
                    // Đảm bảo child không null trước khi mapping
                    Child child = booking.getChild();
                    List<VaccineOrderDTO> orderDTOs = booking.getVaccineOrders() != null ? 
                        booking.getVaccineOrders().stream()
                            .map(VaccineOrderDTO::new)
                            .collect(Collectors.toList()) : 
                        null;
                    
                    return new BookingResponse(
                        booking.getBookingId(),
                        booking.getAppointmentDate(),
                        child,
                        orderDTOs,
                        booking.getStatus()
                    );
                } catch (Exception e) {
                    // Log lỗi và bỏ qua booking có vấn đề
                    log.error("Error mapping booking with ID {}: {}", booking.getBookingId(), e.getMessage());
                    return null;
                }
            })
            .filter(response -> response != null) // Loại bỏ các response null do lỗi
            .collect(Collectors.toList());
    }

    @Transactional
    @SuppressWarnings("rawtypes")
    public ApiResponse assignStaff(int bookingId) {
        try {
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

            if (booking.getStatus() != BookingStatus.CHECKED_IN) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in CHECKED_IN status")
                        .build();
            }

            // 1. Lấy ngày hẹn từ booking
            Date appointmentDate = booking.getAppointmentDate();

            // 2. Tìm WorkDate tương ứng với ngày hẹn
            WorkDate workDate = workingDateRepo.findByDayWork(appointmentDate)
                    .orElseThrow(() -> new AppException(ErrorCode.WORK_DATE_NOT_FOUND));

            // 3. Tìm danh sách nhân viên làm việc vào ngày đó
            List<WorkingSchedule> workingSchedules = workingScheduleRepo.findByDateId(workDate.getId());

            if (workingSchedules.isEmpty()) {
                return ApiResponse.builder()
                        .code(400)
                        .message("No staff available on the appointment date")
                        .build();
            }

            // 4. Tìm bác sĩ và y tá trong danh sách nhân viên
            Account doctor = null;
            Account nurse = null;

            for (WorkingSchedule schedule : workingSchedules) {
                Account staff = schedule.getAccount();
                // Kiểm tra xem nhân viên có role bác sĩ không
                if (doctor == null && hasRole(staff, "DOCTOR")) {
                    doctor = staff;
                }
                // Kiểm tra xem nhân viên có role y tá không
                else if (nurse == null && hasRole(staff, "NURSE")) {
                    nurse = staff;
                }

                // Nếu đã tìm thấy cả bác sĩ và y tá, dừng vòng lặp
                if (doctor != null && nurse != null) {
                    break;
                }
            }

            if (doctor == null) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Doctor not available on the appointment date")
                        .build();
            }

            // 5. Tạo bản ghi Diagnosis với bác sĩ được chỉ định
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setBooking(booking);
            diagnosis.setAccount(doctor);
            // Các trường khác sẽ được cập nhật sau khi bác sĩ thực hiện chẩn đoán
            diagnosisRepo.save(diagnosis);

            // 6. Cập nhật trạng thái booking
            booking.setStatus(BookingStatus.ASSIGNED);
            bookingRepo.save(booking);

            // 7. Trả về thông tin bác sĩ và y tá đã được chỉ định
            Map<String, Object> assignmentInfo = new HashMap<>();
            assignmentInfo.put("booking", new BookingDTO(booking));
            assignmentInfo.put("assignedDoctor", doctor.getFirstName() + " " + doctor.getLastName());
            if (nurse != null) {
                assignmentInfo.put("availableNurse", nurse.getFirstName() + " " + nurse.getLastName());
            }

            return ApiResponse.builder()
                    .code(200)
                    .message("Staff assigned successfully")
                    .result(assignmentInfo)
                    .build();
        } catch (AppException e) {
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .code(500)
                    .message("Error assigning staff: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Update booking status to CHECKED_IN (when patient arrives)
     * @param bookingId Booking ID
     * @return API response
     */
    @SuppressWarnings("rawtypes")
    public ApiResponse checkIn(int bookingId) {
        try {
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
            
            if (booking.getStatus() != BookingStatus.PAID) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in PAID status")
                        .build();
            }
            
            booking.setStatus(BookingStatus.CHECKED_IN);
            bookingRepo.save(booking);
            
            return ApiResponse.builder()
                    .code(200)
                    .message("Check-in successful")
                    .result(new BookingDTO(booking))
                    .build();
        } catch (AppException e) {
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .code(500)
                    .message("Error during check-in: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Update booking status to PAID (after payment)
     * @param bookingId Booking ID
     * @return API response
     */
    @SuppressWarnings("rawtypes")
    public ApiResponse waitingForPayment(int bookingId) {
        try {
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

            booking.setStatus(BookingStatus.PAID);
            bookingRepo.save(booking);

            return ApiResponse.builder()
                    .code(200)
                    .message("Payment status updated successfully")
                    .result(new BookingDTO(booking))
                    .build();
        } catch (AppException e) {
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .code(500)
                    .message("Error updating payment status: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Record post-vaccination reaction
     * @param bookingId The booking ID
     * @param reaction The reaction details
     * @return ApiResponse with result
     */
    @SuppressWarnings("rawtypes")
    public ApiResponse recordVaccinationReaction(int bookingId, String reaction) {
        try {
            // Validate booking exists
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // Validate booking status
            if (booking.getStatus() != BookingStatus.VACCINE_INJECTED) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in VACCINE_INJECTED status to record reaction")
                        .build();
            }

            // Update booking with reaction
            booking.setReaction(reaction);
            booking.setStatus(BookingStatus.COMPLETED);
            bookingRepo.save(booking);

            return ApiResponse.builder()
                    .code(200)
                    .message("Vaccination reaction recorded successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error recording vaccination reaction: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Error recording vaccination reaction: " + e.getMessage())
                    .build();
        }
    }

    // Helper method to check if an account has a specific role
    private boolean hasRole(Account account, String roleName) {
        if (account == null || account.getRoles() == null) {
            return false;
        }

        return account.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));
    }
}
