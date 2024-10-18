package com.pmarko09.medical_clinic.validation;

import com.pmarko09.medical_clinic.exception.appointment.AppointmentInThePastException;
import com.pmarko09.medical_clinic.exception.appointment.AppointmentNotAvailableException;
import com.pmarko09.medical_clinic.exception.appointment.AppointmentWrongTimeException;
import com.pmarko09.medical_clinic.model.model.Appointment;
import com.pmarko09.medical_clinic.repository.AppointmentRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentValidation {


    public static void appTimeValidation(LocalDateTime appointmentTime) {
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new AppointmentInThePastException();
        }
        if (appointmentTime.getMinute() % 15 != 0) {
            throw new AppointmentWrongTimeException(appointmentTime);
        }
    }

    public static Appointment appointmentAvailable(AppointmentRepository appRepo, LocalDateTime AppStartTime, LocalDateTime AppFinishTime) {
        List<Appointment> appointments = appRepo.findAll();

        return appointments.stream()
                .filter(appointment -> appointment.getAppStartTime().equals(AppStartTime)
                        && appointment.getAppFinishTime().equals(AppFinishTime)
                        && appointment.getPatient() == null)
                .findFirst()
                .orElseThrow(AppointmentNotAvailableException::new);
    }
}
