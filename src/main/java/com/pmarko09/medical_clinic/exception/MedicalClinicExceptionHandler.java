package com.pmarko09.medical_clinic.exception;

import com.pmarko09.medical_clinic.exception.doctor.DoctorAlreadyExistsException;
import com.pmarko09.medical_clinic.exception.doctor.DoctorEmailNotFoundException;
import com.pmarko09.medical_clinic.exception.doctor.IllegalDoctorDataException;
import com.pmarko09.medical_clinic.exception.hospital.HospitalAlreadyExistsException;
import com.pmarko09.medical_clinic.exception.hospital.HospitalNotFoundException;
import com.pmarko09.medical_clinic.exception.hospital.IllegalHospitalDataException;
import com.pmarko09.medical_clinic.exception.patient.IllegalPatientDataException;
import com.pmarko09.medical_clinic.exception.patient.PatientAlreadyExistException;
import com.pmarko09.medical_clinic.exception.patient.PatientEmailNotFoundException;
import com.pmarko09.medical_clinic.model.dto.ErrorMessageDTO;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientEmailNotFoundException.class)
    protected ResponseEntity<Object> handlePatientNotFound(PatientEmailNotFoundException ex, WebRequest webRequest) {
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

    @ExceptionHandler(DoctorEmailNotFoundException.class)
    protected ResponseEntity<Object> handleDoctorNotFound(DoctorEmailNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(DoctorAlreadyExistsException.class)
    protected ResponseEntity<Object> handleDoctorAlreadyExists(DoctorAlreadyExistsException ex, WebRequest webRequest) {
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

    @ExceptionHandler(HospitalAlreadyExistsException.class)
    protected ResponseEntity<Object> handleHospitalAlreadyExists(HospitalAlreadyExistsException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(HospitalNotFoundException.class)
    protected ResponseEntity<Object> handleHospitalNotFound(HospitalNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalHospitalDataException.class)
    protected ResponseEntity<Object> handleIllegalHospitalData(IllegalHospitalDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
