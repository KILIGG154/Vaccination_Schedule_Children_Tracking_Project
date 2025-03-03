package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //NƠI TA DEFIND CÁC LOẠI ERROR_CODE DỰA THEO CÁC ERROR_MESSAGE ĐỂ TRẢ VỀ CLIENT RÕ RÀNG VỀ LOẠI LỖI MUỐN TRẢ

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR ),

    INVALID_DATA_ACCESS_RESOURCE_USAGE(9998, "Invalid data access resource usage (SQL syntax, table not exist, etc)", HttpStatus.INTERNAL_SERVER_ERROR),

    // Mã lỗi cho ngoại lệ TransientObjectException
    TRANSIENT_OBJECT_EXCEPTION(9997, "A transient object was referenced. (Object save in RAM but not in database)", HttpStatus.INTERNAL_SERVER_ERROR),



    //Check-Output
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST(1002, "User already exist", HttpStatus.CONFLICT),
    USERNAME_NOT_EXIT(1003, "UserName not exist", HttpStatus.NOT_FOUND),
    EMPTY_USER(1004, "Empty user", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1005, "Role not found", HttpStatus.NOT_FOUND),





    //Check     -Input
    INVALID_KEY (2000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2001, "Username must be between 3 and 16 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2002, "Password must be between 3 and 16 characters", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(2003, "Invalid Username or Password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2004, "You do not have permission", HttpStatus.FORBIDDEN),;


    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
