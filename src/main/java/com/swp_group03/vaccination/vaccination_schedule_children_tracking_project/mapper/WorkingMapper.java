package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working.WorkingResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingMapper {

    WorkDate createWorkDate(WorkingRequest request);

    List<WorkingResponse> toGetAllWorking(List<WorkingSchedule> workDates);

}
