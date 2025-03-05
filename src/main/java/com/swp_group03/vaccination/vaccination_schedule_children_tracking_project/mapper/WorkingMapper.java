package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working.WorkingRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkingMapper {

    WorkDate createWorkDate(WorkingRequest request);

}
