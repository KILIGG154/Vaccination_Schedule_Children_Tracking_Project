package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.vaccination;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.ProtocolDetail;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.vaccination.VaccineInjectionRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.BookingRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.ScheduleVaccineRecordRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.UserRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineProtocolDoseRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.VaccineTherapyRecordRepo;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking.StaffAssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VaccinationService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VaccineProtocolDoseRepo protocolDoseRepo;

    @Autowired
    private ScheduleVaccineRecordRepo vaccineRecordRepo;

    @Autowired
    private VaccineTherapyRecordRepo therapyRecordRepo;

    @Autowired
    private StaffAssignmentService staffAssignmentService;

    @SuppressWarnings("rawtypes")
    public ApiResponse recordVaccineInjection(int bookingId, String nurseId, VaccineInjectionRequest request) {
        try {
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

            // Kiểm tra trạng thái booking
            if (booking.getStatus() != BookingStatus.DIAGNOSED) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in DIAGNOSED status to record injection")
                        .build();
            }

            // Tìm thông tin y tá
            Account nurse = userRepo.findById(nurseId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            // Tìm thông tin dose
            VaccineProtocolDose dose = protocolDoseRepo.findById(request.getDoseId())
                    .orElseThrow(() -> new AppException(ErrorCode.PROTOCOL_NOT_FOUND));

            // Tạo vaccine record mới (ghi nhận việc tiêm)
            ScheduleVaccineRecord record = new ScheduleVaccineRecord();
            record.setBooking(booking);
            record.setDose(dose);
            record.setAccount(nurse);
            record.setInjectionDate(LocalDate.now());
            record.setStatus(true); // Đã tiêm

            vaccineRecordRepo.save(record);

            // Cập nhật trạng thái booking
            booking.setStatus(BookingStatus.VACCINE_INJECTED);
            bookingRepo.save(booking);

            return ApiResponse.builder()
                    .code(200)
                    .message("Vaccine injection recorded successfully")
                    .result(record)
                    .build();
        } catch (AppException e) {
            log.error("Error recording vaccine injection: ", e);
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("Error recording vaccine injection: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Error recording vaccine injection: " + e.getMessage())
                    .build();
        }
    }

    @SuppressWarnings("rawtypes")
    public ApiResponse createNextSchedule(int bookingId) {
        try {
            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

            // Kiểm tra trạng thái booking
            if (booking.getStatus() != BookingStatus.COMPLETED) {
                return ApiResponse.builder()
                        .code(400)
                        .message("Booking must be in COMPLETED status")
                        .build();
            }

            // Lấy danh sách vaccine đã tiêm
            List<ScheduleVaccineRecord> vaccineRecords = vaccineRecordRepo.findByBookingBookingId(bookingId);

            List<VaccineTherapyRecord> nextSchedules = new ArrayList<>();

            // Tạo lịch tiêm tiếp theo cho từng vaccine
            for (ScheduleVaccineRecord record : vaccineRecords) {
                VaccineProtocolDose currentDose = record.getDose();

                // Lấy danh sách dose của vaccine
                List<VaccineProtocolDose> doses = protocolDoseRepo
                        .findByVaccineId(currentDose.getVaccine().getId());

                // Sắp xếp theo số thứ tự liều
                doses.sort((d1, d2) -> Integer.compare(d1.getProtocolDetail().getDoseNumber(), d2.getProtocolDetail().getDoseNumber()));

                // Tìm vị trí của dose hiện tại
                int currentIndex = -1;
                for (int i = 0; i < doses.size(); i++) {
                    if (doses.get(i).getDoseId() == currentDose.getDoseId()) {
                        currentIndex = i;
                        break;
                    }
                }

                // Nếu còn dose tiếp theo
                if (currentIndex < doses.size() - 1) {
                    VaccineProtocolDose nextDose = doses.get(currentIndex + 1);
                    ProtocolDetail protocolDetail = nextDose.getProtocolDetail();

                    // Tính ngày tiêm tiếp theo
                    LocalDate injectionDate = record.getInjectionDate();
                    LocalDate nextDate = injectionDate.plusDays(protocolDetail.getIntervalDays());

                    // Tạo therapy record mới (lịch dự kiến)
                    VaccineTherapyRecord therapy = new VaccineTherapyRecord();
                    therapy.setBooking(booking);
                    therapy.setDose(nextDose);
                    therapy.setTherapyDate(nextDate);
                    therapy.setStatus(VaccineTherapyStatus.SCHEDULED);
                    therapy.setCreateAt(LocalDateTime.now());
                    therapy.setUpdateAt(LocalDateTime.now());

                    therapy = therapyRecordRepo.save(therapy);
                    nextSchedules.add(therapy);
                }
            }

            return ApiResponse.builder()
                    .code(200)
                    .message("Next schedules created successfully")
                    .result(nextSchedules)
                    .build();
        } catch (AppException e) {
            log.error("Error creating next schedules: ", e);
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("Error creating next schedules: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Error creating next schedules: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Ghi nhận tiêm vaccine và giải phóng nhân viên
     *
     * @param bookingId ID của booking
     * @param nurseId ID của y tá
     * @param request Thông tin tiêm vaccine
     * @return ApiResponse
     */
    @Transactional
    @SuppressWarnings("rawtypes")
    public ApiResponse recordVaccineInjectionAndReleaseStaff(int bookingId, String nurseId, VaccineInjectionRequest request) {
        try {
            // Ghi nhận tiêm vaccine và cập nhật trạng thái booking
            ApiResponse injectionResponse = recordVaccineInjection(bookingId, nurseId, request);
            
            if (injectionResponse.getCode() != 200) {
                return injectionResponse;
            }
            
            // Giải phóng nhân viên (y tá) sau khi hoàn thành tiêm
            try {
                staffAssignmentService.releaseStaffFromBooking(bookingId);
                log.info("Released nurse after vaccination for booking: {}", bookingId);
            } catch (Exception e) {
                // Log lỗi nhưng vẫn trả về thành công vì việc tiêm đã được ghi nhận
                log.warn("Could not release nurse after vaccination: {}", e.getMessage(), e);
            }
            
            return injectionResponse;
        } catch (Exception e) {
            log.error("Error recording vaccination and releasing staff: ", e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Lỗi khi ghi nhận tiêm vaccine: " + e.getMessage())
                    .build();
        }
    }
}
