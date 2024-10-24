package com.pmarko09.medical_clinic.exception.appointment;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long appId) {
        super(String.format("Appointment with id: %s not found", appId));
    }
}
