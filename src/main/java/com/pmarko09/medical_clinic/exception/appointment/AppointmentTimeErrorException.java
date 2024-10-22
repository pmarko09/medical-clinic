package com.pmarko09.medical_clinic.exception.appointment;

import java.time.LocalDateTime;

public class AppointmentTimeErrorException extends RuntimeException {
    public AppointmentTimeErrorException(LocalDateTime appointmentTime) {
        super(String.format("Appointment for %s is not available. Please choose different time.", appointmentTime));
    }
}
