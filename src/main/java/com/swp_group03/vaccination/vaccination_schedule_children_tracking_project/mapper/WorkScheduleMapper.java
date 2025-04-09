package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.StaffScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkDateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WorkScheduleMapper {

    // Chuyển đổi từ WorkingRequest sang WorkDate
    @Mapping(target = "dateId", ignore = true)
    @Mapping(target = "scheduleName", ignore = true)
    WorkDate workingRequestToWorkDate(WorkingRequest request);

    // Chuyển đổi từ ScheduleRequest sang WorkDate
    @Mapping(target = "dateId", ignore = true)
    @Mapping(target = "dayWork", source = "startDate")
    WorkDate scheduleRequestToWorkDate(ScheduleRequest request);
    
    // Chuyển đổi từ WorkDate và thông tin bổ sung sang ScheduleDTO
    @Mapping(target = "id", source = "workDate.dateId")
    @Mapping(target = "date", source = "workDate.dayWork")
    @Mapping(target = "dayOfWeek", expression = "java(getDayOfWeekString(workDate.getDayWork()))")
    @Mapping(target = "shiftType", source = "workDate.shiftType")
    @Mapping(target = "scheduleName", source = "workDate.scheduleName")
    @Mapping(target = "staff", expression = "java(mapStaffFromWorkingSchedules(schedules, staffMap))")
    ScheduleDTO toScheduleDTO(WorkDate workDate, List<WorkingSchedule> schedules, Map<String, Account> staffMap);
    
    // Phương thức chuyển đổi từ WorkingSchedule và Account sang StaffScheduleDTO
    @Mapping(target = "id", source = "account.accountId")
    @Mapping(target = "name", expression = "java(account.getFirstName() + \" \" + account.getLastName())")
    @Mapping(target = "schedules", ignore = true)
    StaffScheduleDTO toStaffScheduleDTO(Account account, List<WorkingSchedule> schedules);
    
    @Named("mapStaffFromWorkingSchedules")
    default List<StaffDTO> mapStaffFromWorkingSchedules(List<WorkingSchedule> schedules, Map<String, Account> staffMap) {
        if (schedules == null || schedules.isEmpty() || staffMap == null) {
            return List.of();
        }
        
        return schedules.stream()
                .map(schedule -> {
                    Account account = staffMap.get(schedule.getAccountId());
                    if (account == null) {
                        return null;
                    }
                    
                    return StaffDTO.builder()
                            .id(account.getAccountId())
                            .name(account.getFirstName() + " " + account.getLastName())
                            .role(getRole(account))
                            .workStatus(schedule.getWorkStatus().toString())
                            .build();
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
    
    @Named("getRole")
    default String getRole(Account account) {
        return account.getRoles().stream()
                .findFirst()
                .map(role -> role.getRoleName())
                .orElse("Unknown");
    }
    
    // Chuyển đổi StaffScheduleRequest thành WorkingSchedule
    @Mapping(target = "scheduleId", ignore = true)
    @Mapping(target = "dateId", source = "workDate.dateId")
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "schedule", source = "workDate")
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "workStatus", expression = "java(com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkScheduleStatus.OFF_DUTY)")
    WorkingSchedule toWorkingSchedule(StaffScheduleRequest request, WorkDate workDate);
    
    @Named("getFirstStaffId")
    default String getFirstStaffId(List<String> staffIds) {
        if (staffIds == null || staffIds.isEmpty()) {
            return null;
        }
        return staffIds.get(0);
    }
    
    // Ánh xạ từ WorkDate sang WorkDateDTO
    @Mapping(target = "id", source = "dateId")
    @Mapping(target = "dayWork", source = "dayWork")
    @Mapping(target = "shiftType", source = "shiftType")
    WorkDateDTO toWorkDateDTO(WorkDate workDate);

    // Chuyển đổi danh sách WorkingSchedule sang danh sách StaffScheduleDTO
    default List<StaffScheduleDTO> toStaffScheduleDTOs(List<WorkingSchedule> schedules) {
        if (schedules == null) {
            return List.of();
        }
        
        return schedules.stream()
            .map(schedule -> {
                if (schedule.getSchedule() == null || schedule.getAccount() == null) {
                    return null;
                }
                
                return StaffScheduleDTO.builder()
                    .id(schedule.getAccount().getAccountId())
                    .name(schedule.getAccount().getFirstName() + " " + schedule.getAccount().getLastName())
                    .schedules(List.of(WorkDateDTO.builder()
                        .id(schedule.getSchedule().getDateId())
                        .dayWork(schedule.getSchedule().getDayWork())
                        .shiftType(schedule.getSchedule().getShiftType())
                        .build()))
                    .build();
            })
            .filter(dto -> dto != null)
            .collect(Collectors.toList());
    }

    // Phương thức trợ giúp để lấy tên ngày trong tuần
    default String getDayOfWeekString(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
    
    // Phương thức để tạo danh sách WorkDate từ ScheduleRequest
    default List<WorkDate> createWorkDatesFromRequest(ScheduleRequest request) {
        if (request.isRepeatPattern() && request.getWeekdays() != null && !request.getWeekdays().isEmpty()) {
            return createWorkDatesWithRepeatPattern(
                request.getStartDate(), 
                request.getEndDate(), 
                request.getWeekdays(), 
                request.getShiftType(),
                request.getScheduleName()
            );
        } else {
            return createWorkDatesForDateRange(
                request.getStartDate(), 
                request.getEndDate(), 
                request.getShiftType(),
                request.getScheduleName()
            );
        }
    }
    
    default List<WorkDate> createWorkDatesWithRepeatPattern(
            LocalDate startDate, 
            LocalDate endDate, 
            List<Integer> weekdays, 
            String shiftType,
            String scheduleName) {
        
        return startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> {
                    int dayOfWeek = date.getDayOfWeek().getValue();
                    return weekdays.contains(dayOfWeek);
                })
                .map(date -> new WorkDate(date, shiftType, scheduleName))
                .collect(Collectors.toList());
    }

    default List<WorkDate> createWorkDatesForDateRange(
            LocalDate startDate, 
            LocalDate endDate, 
            String shiftType,
            String scheduleName) {
        
        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> new WorkDate(date, shiftType, scheduleName))
                .collect(Collectors.toList());
    }
} 