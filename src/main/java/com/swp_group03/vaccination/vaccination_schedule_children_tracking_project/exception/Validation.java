package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Validation {

   // MethodArgumentNotValidException: quanng loi tu thu vien ra
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException ex){

        String message = "";
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            message += fieldError.getDefaultMessage();

        }
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
