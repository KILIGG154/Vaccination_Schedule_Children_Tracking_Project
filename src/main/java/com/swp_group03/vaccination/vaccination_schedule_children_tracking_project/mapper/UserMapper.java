package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserCeation;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.UserUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Account.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Account toCreateUser(UserCeation request);

    @Mapping(target = "roles", ignore = true)
    Account toUpdateUser(UserUpdate request);

    // Mapping từng Account thành AccountResponse, bao gồm roleName
    @Mapping(target = "roleName", source = "roles", qualifiedByName = "mapRolesToRoleName")
    AccountResponse toAccountResponsee(Account account);

    // Mapping List<Account> thành List<AccountResponse>
    List<AccountResponse> toAllAccountResponse(List<Account> accountList);

    // Custom method để lấy Role đầu tiên trong danh sách
    @Named("mapRolesToRoleName")
    default String mapRolesToRoleName(Set<Role> roles) {
        return roles.stream()
                .findFirst()  // Lấy role đầu tiên
                .map(Role::getRoleName) // Lấy name của Role
                .orElse("Unknown"); // Nếu không có role, trả về "Unknown"
    }


    AccountResponse toAccountResponse(Optional<Account> account);
}