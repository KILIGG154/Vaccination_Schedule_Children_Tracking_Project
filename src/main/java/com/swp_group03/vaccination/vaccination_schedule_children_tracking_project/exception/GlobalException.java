package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalException {


    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ApiResponse apiResponse = new ApiResponse();

        log.error("Exception occurred: {}", e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgNotValidExcept(MethodArgumentNotValidException e){
        String enumkey = e.getFieldError().getDefaultMessage();
        log.error("Exception occurred: {}", e.getMessage(), e);

        ErrorCode errorCode;
            try {
                errorCode = ErrorCode.valueOf(enumkey);
            } catch (IllegalArgumentException ex) {
                errorCode = ErrorCode.INVALID_KEY;
            }

           ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiResponse> handlingInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        ApiResponse apiResponse = new ApiResponse();

        log.error("Exception occurred: {}", e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.INVALID_DATA_ACCESS_RESOURCE_USAGE;
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = TransientObjectException.class)
    public ResponseEntity<ApiResponse> handlingTransientObjectException(TransientObjectException e) {
        log.error("TransientObjectException occurred: {}", e.getMessage(), e);

        ApiResponse apiResponse = new ApiResponse();

        ErrorCode errorCode = ErrorCode.TRANSIENT_OBJECT_EXCEPTION;
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }




    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handlingOtherException(Exception e){
        ApiResponse apiResponse = new ApiResponse();

        log.error("Exception occurred: {}", e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build()
        );
    }
}
