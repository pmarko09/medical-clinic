package com.pmarko09.medical_clinic.exception.appointment;

import java.time.LocalDateTime;

public class AppointmentFullQuarterException extends RuntimeException {
    public AppointmentFullQuarterException(LocalDateTime appointmentTimeSelected) {
        super("Appointment time: " + appointmentTimeSelected + " is wrong. Appointments can be booked only every full quarter of hour.");
    }
}
