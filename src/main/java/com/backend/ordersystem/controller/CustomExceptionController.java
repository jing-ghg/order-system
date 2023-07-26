package com.backend.ordersystem.controller;

import com.backend.ordersystem.domain.CustomException;
import com.backend.ordersystem.domain.CustomResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(CustomResponseStatusException.class)
    public ResponseEntity<CustomException> handleCustomResponseStatusException(CustomResponseStatusException ex) {
        CustomException errorResponse = ex.getErrorResponse();
        HttpStatus status = ex.getStatusCode();
        return new ResponseEntity<>(errorResponse, status);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleCustomMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CustomException errorResponse = new CustomException(ex.getMessage());
        HttpStatusCode status = ex.getStatusCode();
        return new ResponseEntity<>(errorResponse, status);

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomException> handleCustomMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        CustomException errorResponse = new CustomException(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomException> handleCustomException(Exception ex) {
        CustomException errorResponse = new CustomException(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


}