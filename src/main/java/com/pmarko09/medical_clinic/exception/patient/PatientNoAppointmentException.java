package com.pmarko09.medical_clinic.exception.patient;

public class PatientNoAppointmentException extends RuntimeException {
    public PatientNoAppointmentException(Long patientId) {
        super(String.format("Patient with id: %s has no appointment booked.", patientId));
    }
}
