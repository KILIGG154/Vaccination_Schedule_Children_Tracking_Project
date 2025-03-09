package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.Working;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;

public class WorkingResponse {
    private int dateId;
    private String accountId;
    private WorkDate date;
    private Account account;
    private String status;

    public WorkingResponse() {
    }

    public WorkingResponse(int dateId, String accountId, WorkDate date, Account account, String status) {
        this.dateId = dateId;
        this.accountId = accountId;
        this.date = date;
        this.account = account;
        this.status = status;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
