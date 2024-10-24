package com.pmarko09.medical_clinic.exception.patient;

public class PatientHasBookedAppointmentException extends RuntimeException {
    public PatientHasBookedAppointmentException(Long patientId, Long appointmentId) {
        super(String.format("Patient with id: %s has been already booked for appointment with id %s.", patientId, appointmentId));
    }
}
