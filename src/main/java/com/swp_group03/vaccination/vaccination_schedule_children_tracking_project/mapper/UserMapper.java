package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Account toUser(UserRequest request);
}
