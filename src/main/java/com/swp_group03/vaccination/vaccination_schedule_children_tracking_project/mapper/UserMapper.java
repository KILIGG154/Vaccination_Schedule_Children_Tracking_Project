package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.mapper;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Role;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountCreate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.account.AccountUpdate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account.AccountResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "status", ignore = true) // Nếu status có giá trị mặc định trong entity, có thể bỏ qua
    @Mapping(target = "roles", ignore = true)  // Không set roles khi tạo mới
    Account toCreateUser(AccountCreate request);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
//    @Mapping(target = "firstName", source = "request.firstName")
//    @Mapping(target = "lastName", source = "request.lastName")
//    @Mapping(target = "email", source = "request.email")
//    @Mapping(target = "phoneNumber", source = "request.phoneNumber")
//    @Mapping(target = "address", source = "request.address")
//    @Mapping(target = "gender", source = "request.gender")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account toUpdateUser(AccountUpdate request, @MappingTarget Account account);

    @Mapping(target = "roleName", source = "roles", qualifiedByName = "mapRolesToRoleName")
//    @Mapping(target = "accountId", source = "account.accountId")
//    @Mapping(target = "username", source = "account.username")
//    @Mapping(target = "firstName", source = "account.firstName")
//    @Mapping(target = "lastName", source = "account.lastName")
//    @Mapping(target = "email", source = "account.email")
//    @Mapping(target = "phoneNumber", source = "account.phoneNumber")
//    @Mapping(target = "address", source = "account.address")
//    @Mapping(target = "gender", source = "account.gender")
//    @Mapping(target = "status", source = "account.status")
    AccountResponse toAccountResponse(Account account);

    List<AccountResponse> toAllAccountResponse(List<Account> accountList);

    @Named("mapRolesToRoleName")
    default String mapRolesToRoleName(Set<Role> roles) {
        return roles.stream()
                .findFirst()
                .map(Role::getRoleName)
                .orElse("Unknown");
    }

    default AccountResponse toAccountResponse(Optional<Account> account) {
        return account.map(this::toAccountResponse).orElse(null);
    }
}
