package com.pmarko09.medical_clinic.exception.appointment;

public class AppointmentTimeErrorException extends RuntimeException {
    public AppointmentTimeErrorException() {
        super("Requested appointment time is not available.");
    }
}
