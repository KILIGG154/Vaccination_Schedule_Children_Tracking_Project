package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO chứa thông tin cơ bản của tài khoản, được sử dụng ở các DTO khác
 * để tránh expose quá nhiều thông tin tài khoản
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountBasicInfo {
    private String accountId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public AccountBasicInfo(Account account) {
        if (account != null) {
            this.accountId = account.getAccountId();
            this.username = account.getUsername();
            this.firstName = account.getFirstName();
            this.lastName = account.getLastName();
            this.email = account.getEmail();
            this.phoneNumber = account.getPhoneNumber();
        }
    }
} 