package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlingOtherException(Exception e){
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ApiResponse apiResponse = new ApiResponse();

        ErrorCode errorCode = e.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgNotValidExcept(MethodArgumentNotValidException e){
            String enumkey = e.getFieldError().getDefaultMessage();

            ErrorCode errorCode;
            try {
                errorCode = ErrorCode.valueOf(enumkey);
            } catch (IllegalArgumentException ex) {
                errorCode = ErrorCode.INVALID_KEY;
            }

           ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());

            return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiResponse> handlingInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.INVALID_DATA_ACCESS_RESOURCE_USAGE.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_DATA_ACCESS_RESOURCE_USAGE.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
