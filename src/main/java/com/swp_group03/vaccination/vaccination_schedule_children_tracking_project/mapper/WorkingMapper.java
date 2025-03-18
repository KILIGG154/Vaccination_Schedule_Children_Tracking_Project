//package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;
//
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.ScheduleResponse;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkDateDTO;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working.WorkingResponse;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class WorkingMapper {
//
//    public WorkDate createWorkDate(WorkingRequest request) {
//        WorkDate workDate = new WorkDate();
//        workDate.setDayWork(request.getDayWork());
//        workDate.setShiftType(request.getShiftType());
//        return workDate;
//    }
//
//    public List<WorkingResponse> toGetAllWorking(List<WorkingSchedule> schedules) {
//        return schedules.stream()
//                .map(schedule -> {
//                    WorkingResponse response = new WorkingResponse();
//                    response.setDateId(schedule.getDateId());
//                    response.setAccountId(schedule.getAccountId());
//
//                    // Convert WorkDate to WorkDateDTO
//                    WorkDateDTO dateDTO = new WorkDateDTO();
//                    WorkDate workDate = schedule.getSchedule();
//                    dateDTO.setId(workDate.getId());
//                    dateDTO.setDayWork(workDate.getDayWork());
//                    dateDTO.setShiftType(workDate.getShiftType());
//                    response.setDate(dateDTO);
//
//                    response.setStatus(schedule.isStatus() ? "Active" : "Inactive");
//                    return response;
//                })
//                .collect(Collectors.toList());
//    }
//
//    public WorkDate scheduleRequestToWorkDate(ScheduleRequest request) {
//        WorkDate workDate = new WorkDate();
//        workDate.setDayWork(request.getStartDate());
//        workDate.setShiftType(request.getShiftType());
//        return workDate;
//    }
//
//    public ScheduleResponse toScheduleResponse(String scheduleName, String shiftType, List<WorkDate> workDates) {
//        ScheduleResponse response = new ScheduleResponse();
//        response.setScheduleName(scheduleName);
//        response.setShiftType(shiftType);
//        response.setWorkDates(workDates);
//        return response;
//    }
//}