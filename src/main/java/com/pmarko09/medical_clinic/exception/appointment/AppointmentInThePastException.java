package com.pmarko09.medical_clinic.exception.appointment;

public class AppointmentInThePastException extends RuntimeException {
    public AppointmentInThePastException() {
        super("Appointment can't be scheduled in the past. Please provide correct time in the future.");
    }
}
