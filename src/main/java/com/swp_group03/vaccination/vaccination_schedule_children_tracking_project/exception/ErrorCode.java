package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // General errors
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DATA_ACCESS_RESOURCE_USAGE(9998, "Invalid data access resource usage (SQL syntax, table not exist, etc)", HttpStatus.INTERNAL_SERVER_ERROR),
    TRANSIENT_OBJECT_EXCEPTION(9997, "A transient object was referenced. (Object saved in RAM but not in database)", HttpStatus.INTERNAL_SERVER_ERROR),

    // User-related errors
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST(1002, "User already exists", HttpStatus.CONFLICT),
    USERNAME_NOT_EXIST(1003, "Username does not exist", HttpStatus.NOT_FOUND),
    EMPTY_USER(1004, "User data is empty", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1010, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_ROLE(1011, "Invalid role specified", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1012, "Vaccine Category not found", HttpStatus.NOT_FOUND),
    VACCINE_NOT_FOUND(1013, "Vaccine not found", HttpStatus.NOT_FOUND),
    PROTOCOL_NOT_FOUND(1014, "Protocol not found", HttpStatus.NOT_FOUND),
    BOOKING_NOT_FOUND(1015, "Booking not found", HttpStatus.NOT_FOUND),
    WORK_DATE_NOT_FOUND(1016, "Work Date not found", HttpStatus.NOT_FOUND),
    DIAGNOSIS_NOT_FOUND(1017, "Diagnosis not found", HttpStatus.NOT_FOUND),

    // Input validation errors
    INVALID_KEY(2000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2001, "Username must be between 3 and 16 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2002, "Password must be between 3 and 16 characters", HttpStatus.BAD_REQUEST),
    INVALID_DOSE_COUNT(2003, "Total Dose cannot exceed 5", HttpStatus.BAD_REQUEST),

    // Authorization errors
    UNAUTHENTICATED(4001, "Invalid Username or Password", HttpStatus .UNAUTHORIZED),
    UNAUTHORIZED(4002, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(4003, "Invalid token", HttpStatus.UNAUTHORIZED),

    //payment
    PAYMENT_FAIL(4221,"Something went wrong", HttpStatus.NOT_FOUND),;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
