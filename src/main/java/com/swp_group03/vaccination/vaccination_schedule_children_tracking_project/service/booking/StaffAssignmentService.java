package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.service.booking;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.*;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.vaccine.VaccineProtocolDose;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.AppException;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service để quản lý việc gán nhân viên cho booking
 */
@Service
@Slf4j
public class StaffAssignmentService {

    @Autowired
    private WorkingDateRepo workingDateRepo;
    
    @Autowired
    private WorkingScheduleRepo workingScheduleRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private BookingRepo bookingRepo;
    
    @Autowired
    private DiagnosisRepo diagnosisRepo;
    
    @Autowired
    private ScheduleVaccineRecordRepo vaccineRecordRepo;
    
    @Autowired
    private VaccineProtocolDoseRepo protocolDoseRepo;

    /**
     * Tìm và gán nhân viên thích hợp cho một booking
     * 
     * @param bookingId ID của booking
     * @param role Vai trò cần tìm (DOCTOR, NURSE)
     * @param bookingDate Ngày của booking
     * @return WorkingSchedule của nhân viên được gán
     * @throws AppException nếu không tìm thấy nhân viên hoặc workdate phù hợp
     */
    @Transactional
    public WorkingSchedule assignStaffToBooking(int bookingId, String role, Date bookingDate) {
        // 1. Tìm booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        
        // 2. Tìm workDate tương ứng với ngày hẹn
        WorkDate workDate = workingDateRepo.findByDayWork(bookingDate)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_DATE_NOT_FOUND));
        
        // 3. Tìm danh sách lịch làm việc của tất cả nhân viên vào ngày đó
        List<WorkingSchedule> allStaffSchedules = workingScheduleRepo.findBySchedule(workDate);
        
        // 4. Lọc nhân viên theo vai trò và trạng thái sẵn sàng
        List<WorkingSchedule> availableStaffSchedules = allStaffSchedules.stream()
                .filter(schedule -> 
                    schedule.isStatus() && 
                    schedule.getWorkStatus() == WorkScheduleStatus.AVAILABLE && 
                    hasRole(schedule.getAccount(), role))
                .collect(Collectors.toList());
        
        if (availableStaffSchedules.isEmpty()) {
            throw new AppException(ErrorCode.NO_AVAILABLE_STAFF);
        }
        
        // 5. Sắp xếp để cân bằng tải (chọn nhân viên có ít lịch làm việc nhất)
        WorkingSchedule selectedSchedule = availableStaffSchedules.stream()
                .min(Comparator.comparingInt(this::countAssignedBookings))
                .orElseThrow(() -> new AppException(ErrorCode.SYSTEM_ERROR));
        
        // 6. Cập nhật trạng thái của nhân viên được chọn
        selectedSchedule.setWorkStatus(WorkScheduleStatus.ASSIGNED);
        workingScheduleRepo.save(selectedSchedule);
        
        // 7. Tạo bản ghi trung gian (Diagnosis cho DOCTOR, ScheduleVaccineRecord cho NURSE)
        Account selectedStaff = selectedSchedule.getAccount();
        
        if ("DOCTOR".equalsIgnoreCase(role)) {
            // Tạo bản ghi Diagnosis cho bác sĩ
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setBooking(booking);
            diagnosis.setAccount(selectedStaff);
            diagnosisRepo.save(diagnosis);
            log.info("Đã tạo bản ghi Diagnosis cho bác sĩ {} và booking {}", 
                    selectedStaff.getAccountId(), bookingId);
        } else if ("NURSE".equalsIgnoreCase(role)) {
            // Tạo bản ghi ScheduleVaccineRecord cho y tá
            // Lấy VaccineProtocolDose từ booking (giả sử có vaccine order đầu tiên)
            Optional<VaccineProtocolDose> protocolDose = protocolDoseRepo.findFirstByBookingId(bookingId);
            
            if (protocolDose.isPresent()) {
                ScheduleVaccineRecord vaccineRecord = new ScheduleVaccineRecord();
                vaccineRecord.setBooking(booking);
                vaccineRecord.setAccount(selectedStaff);
                vaccineRecord.setDose(protocolDose.get());
                vaccineRecord.setInjectionDate(new Date());  // Ngày hiện tại
                vaccineRecord.setStatus(false);  // Chưa tiêm
                vaccineRecordRepo.save(vaccineRecord);
                log.info("Đã tạo bản ghi ScheduleVaccineRecord cho y tá {} và booking {}", 
                        selectedStaff.getAccountId(), bookingId);
            } else {
                // Nếu booking chưa có liều vaccine, vẫn tạo bản ghi nhưng không gán dose
                ScheduleVaccineRecord vaccineRecord = new ScheduleVaccineRecord();
                vaccineRecord.setBooking(booking);
                vaccineRecord.setAccount(selectedStaff);
                vaccineRecord.setInjectionDate(new Date());  // Ngày hiện tại
                vaccineRecord.setStatus(false);  // Chưa tiêm
                vaccineRecordRepo.save(vaccineRecord);
                log.info("Đã tạo bản ghi ScheduleVaccineRecord cho y tá {} và booking {} (không có liều vaccine)", 
                        selectedStaff.getAccountId(), bookingId);
            }
        }
        
        return selectedSchedule;
    }
    
    /**
     * Giải phóng nhân viên sau khi hoàn thành công việc
     * 
     * @param bookingId ID của booking
     * @param role Vai trò của nhân viên (DOCTOR, NURSE)
     * @return WorkingSchedule đã được cập nhật
     */
    @Transactional
    public WorkingSchedule releaseStaffFromBooking(int bookingId, String role) {
        // 1. Tìm nhân viên đã được gán cho booking này dựa trên entity trung gian
        Account staffAccount = null;
        
        if ("DOCTOR".equalsIgnoreCase(role)) {
            // Tìm bác sĩ từ Diagnosis
            Optional<Diagnosis> diagnosis = diagnosisRepo.findByBookingId(bookingId);
            if (diagnosis.isPresent()) {
                staffAccount = diagnosis.get().getAccount();
            }
        } else if ("NURSE".equalsIgnoreCase(role)) {
            // Tìm y tá từ ScheduleVaccineRecord
            Optional<ScheduleVaccineRecord> vaccineRecord = vaccineRecordRepo.findByBookingId(bookingId);
            if (vaccineRecord.isPresent()) {
                staffAccount = vaccineRecord.get().getAccount();
            }
        }
        
        if (staffAccount == null) {
            log.warn("Không tìm thấy nhân viên {} nào được gán cho booking ID: {}", role, bookingId);
            return null;
        }
        
        // 2. Tìm booking để lấy ngày
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        
        // 3. Tìm ngày làm việc
        WorkDate workDate = workingDateRepo.findByDayWork(booking.getAppointmentDate())
                .orElseThrow(() -> new AppException(ErrorCode.WORK_DATE_NOT_FOUND));
        
        // 4. Tìm lịch làm việc của nhân viên này vào ngày hẹn
        Optional<WorkingSchedule> staffSchedule = workingScheduleRepo.findByScheduleAndAccount(workDate, staffAccount);
        
        if (staffSchedule.isEmpty()) {
            log.warn("Không tìm thấy lịch làm việc của nhân viên {} vào ngày {}", 
                    staffAccount.getAccountId(), booking.getAppointmentDate());
            return null;
        }
        
        // 5. Cập nhật trạng thái nhân viên
        WorkingSchedule schedule = staffSchedule.get();
        schedule.setWorkStatus(WorkScheduleStatus.AVAILABLE);
        
        return workingScheduleRepo.save(schedule);
    }
    
    /**
     * Giải phóng nhân viên sau khi hoàn thành công việc - phiên bản đơn giản
     * 
     * @param bookingId ID của booking
     * @return WorkingSchedule đã được cập nhật
     */
    @Transactional
    public WorkingSchedule releaseStaffFromBooking(int bookingId) {
        // Phiên bản đơn giản: Thử cả hai vai trò
        WorkingSchedule result = releaseStaffFromBooking(bookingId, "DOCTOR");
        if (result == null) {
            result = releaseStaffFromBooking(bookingId, "NURSE");
        }
        return result;
    }
    
    /**
     * Đếm số lượng booking đã gán cho một nhân viên
     */
    private int countAssignedBookings(WorkingSchedule schedule) {
        // Đếm số lượng lịch làm việc ASSIGNED của nhân viên
        return (int) workingScheduleRepo.countByAccountAndWorkStatus(
                schedule.getAccount(), 
                WorkScheduleStatus.ASSIGNED
        );
    }
    
    /**
     * Kiểm tra xem một tài khoản có vai trò cụ thể không
     */
    private boolean hasRole(Account account, String roleName) {
        if (account == null || account.getRoles() == null) {
            return false;
        }
        
        return account.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));
    }
} 