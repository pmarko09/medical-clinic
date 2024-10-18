package com.pmarko09.medical_clinic.exception.appointment;

public class AppointmentNotAvailableException extends RuntimeException {
    public AppointmentNotAvailableException() {
        super("Appointment is not available in requested time.");
    }
}
