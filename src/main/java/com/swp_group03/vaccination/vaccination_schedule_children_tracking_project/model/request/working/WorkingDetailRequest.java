package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.request.working;

public class WorkingDetailRequest {

    private int dateID;
    private String accountID;
    private boolean status;

    public WorkingDetailRequest() {
    }

    public WorkingDetailRequest(int dateID, String accountID, boolean status) {
        this.dateID = dateID;
        this.accountID = accountID;
        this.status = status;
    }

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
