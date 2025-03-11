package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.ScheduleRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.ScheduleResponse;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.WorkingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingMapper {

    WorkDate createWorkDate(WorkingRequest request);

    List<WorkingResponse> toGetAllWorking(List<WorkingSchedule> workDates);
    
    // Chuyển đổi từ ScheduleRequest sang WorkDate
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "startDate", target = "dayWork")
    @Mapping(source = "shiftType", target = "shiftType")
//    @Mapping(target = "endTime", constant = "18:00")
    @Mapping(target = "workingSchedules", ignore = true)
    WorkDate scheduleRequestToWorkDate(ScheduleRequest request);
    
    // Chuyển đổi từ danh sách WorkDate sang ScheduleResponse
    default ScheduleResponse toScheduleResponse(String scheduleName, String shiftType, List<WorkDate> workDates) {
        ScheduleResponse response = new ScheduleResponse();
        response.setScheduleName(scheduleName);
        response.setShiftType(shiftType);
        response.setWorkDates(workDates);
        return response;
    }
}
