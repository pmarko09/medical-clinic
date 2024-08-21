package com.pmarko09.medical_clinic.exception;

import com.pmarko09.medical_clinic.model.ErrorMessageDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<Object> handlePatientNotFound(PatientNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(PatientAlreadyExistException.class)
    protected ResponseEntity<Object> handlePatientAlreadyExists(PatientAlreadyExistException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(IllegalPatientDataException.class)
    protected ResponseEntity<Object> handleIllegalPatientData(IllegalPatientDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    protected ResponseEntity<Object> handleDoctorNotFound(DoctorNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(DoctorAlreadyExistException.class)
    protected ResponseEntity<Object> handleDoctorAlreadyExists(DoctorAlreadyExistException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(IllegalDoctorDataException.class)
    protected ResponseEntity<Object> handleIllegalDoctorData(IllegalDoctorDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest webRequest) {
        String responseBody = "Unknown error";
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}