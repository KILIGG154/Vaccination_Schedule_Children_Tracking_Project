package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.StaffScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.StaffScheduleDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkDateDTO;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    @Mapping(target = "staffId", source = "account.accountId")
    @Mapping(target = "staffName", expression = "java(account.getFirstName() + \" \" + account.getLastName())")
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
    @Mapping(target = "accountId", source = "request.staffId")
    @Mapping(target = "schedule", source = "workDate")
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "workStatus", expression = "java(com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkScheduleStatus.OFF_DUTY)")
    WorkingSchedule toWorkingSchedule(StaffScheduleRequest request, WorkDate workDate);
    
    // Tạo ScheduleResponse
    @Mapping(target = "scheduleName", source = "scheduleName")
    @Mapping(target = "shiftType", source = "shiftType")
    @Mapping(target = "workDates", source = "workDates")
    ScheduleResponse toScheduleResponse(String scheduleName, String shiftType, List<WorkDate> workDates);
    
    // Phương thức trợ giúp để lấy tên ngày trong tuần
    default String getDayOfWeekString(Date date) {
        String[] DAYS_OF_WEEK = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Convert from Calendar.DAY_OF_WEEK (Sunday = 1) to our format (Monday = 1)
        int ourDayOfWeek = dayOfWeek == Calendar.SUNDAY ? 7 : dayOfWeek - 1;
        return DAYS_OF_WEEK[ourDayOfWeek];
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
            Date startDate, 
            Date endDate, 
            List<Integer> weekdays, 
            String shiftType,
            String scheduleName) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        
        return createDatesBetween(startDate, endDate).stream()
                .filter(date -> {
                    calendar.setTime(date);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    // Convert from Calendar.DAY_OF_WEEK (Sunday = 1) to our format (Monday = 1)
                    int ourDayOfWeek = dayOfWeek == Calendar.SUNDAY ? 7 : dayOfWeek - 1;
                    return weekdays.contains(ourDayOfWeek);
                })
                .map(date -> new WorkDate(date, shiftType, scheduleName))
                .collect(Collectors.toList());
    }

    default List<WorkDate> createWorkDatesForDateRange(
            Date startDate, 
            Date endDate, 
            String shiftType,
            String scheduleName) {
        
        return createDatesBetween(startDate, endDate).stream()
                .map(date -> new WorkDate(date, shiftType, scheduleName))
                .collect(Collectors.toList());
    }

    default List<Date> createDatesBetween(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        
        List<Date> dates = new java.util.ArrayList<>();
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DATE, 1); // Add one day to include the end date
        
        while (calendar.getTime().before(endCalendar.getTime())) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        
        return dates;
    }

    // Ánh xạ từ WorkDate sang WorkDateDTO
    @Mapping(target = "id", source = "dateId")
    @Mapping(target = "dayWork", source = "dayWork")
    @Mapping(target = "shiftType", source = "shiftType")
    WorkDateDTO toWorkDateDTO(WorkDate workDate);

    // Chuyển đổi danh sách WorkingSchedule sang danh sách WorkingResponse
    default List<WorkingResponse> toGetAllWorking(List<WorkingSchedule> schedules) {
        if (schedules == null) {
            return List.of();
        }
        
        return schedules.stream()
            .map(schedule -> {
                if (schedule.getSchedule() == null || schedule.getAccount() == null) {
                    return null;
                }
                
                return WorkingResponse.builder()
                    .dateId(schedule.getSchedule().getDateId())
                    .accountId(schedule.getAccount().getAccountId())
                    .date(WorkDateDTO.builder()
                        .id(schedule.getSchedule().getDateId())
                        .dayWork(schedule.getSchedule().getDayWork())
                        .shiftType(schedule.getSchedule().getShiftType())
                        .build())
                    .status(schedule.isStatus() ? "Active" : "Inactive")
                    .build();
            })
            .filter(response -> response != null)
            .collect(Collectors.toList());
    }
} 