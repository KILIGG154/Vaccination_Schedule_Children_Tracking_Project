package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;

public enum ErrorCode {
    //NƠI TA DEFIND CÁC LOẠI ERROR_CODE DỰA THEO CÁC ERROR_MESSAGE ĐỂ TRẢ VỀ CLIENT RÕ RÀNG VỀ LOẠI LỖI MUỐN TRẢ

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    INVALID_DATA_ACCESS_RESOURCE_USAGE(9998, "Invalid data access resource usage (SQL syntax, table not exist, etc)"),

    //Check-Output
    USER_NOT_FOUND(1001, "User not found"),
    USER_ALREADY_EXIST(1002, "User already exist"),
    USERNAME_NOT_EXIT(1003, "UserName not exist"),
    EMPTY_USER(1004, "Empty user"),


    //Check     -Input
    INVALID_KEY (2000, "Invalid message key"),
    USERNAME_INVALID(2001, "Username must be between 3 and 16 characters"),
    PASSWORD_INVALID(2002, "Password must be between 3 and 16 characters"),
    UNAUTHENTICATED(2003, "Invalid Username or Password"),;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
