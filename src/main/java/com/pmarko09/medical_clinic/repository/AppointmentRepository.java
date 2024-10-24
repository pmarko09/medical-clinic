package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.model.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllApp(Pageable pageable);

    @Query("select a " +
            "from Appointment a " +
            "where :doctorId = a.doctor.id " +
            "and a.appointmentStartTime <= :appointmentFinishTime " +
            "and a.appointmentFinishTime >= :appointmentStartTime")
    List<Appointment> findOverlappingAppointmentsForDoctor(Long doctorId, LocalDateTime appointmentStartTime,
                                                           LocalDateTime appointmentFinishTime);
    @Query("select a " +
            "from Appointment a " +
            "where :patientId = a.patient.id " +
            "and a.appointmentStartTime <= :appointmentFinishTime " +
            "and a.appointmentFinishTime >= :appointmentStartTime")
    List<Appointment> findOverlappingAppointmentsForPatient(Long patientId, LocalDateTime appointmentStartTime,
                                                            LocalDateTime appointmentFinishTime);

    @Query("select a from Appointment a where a.patient.id = :patientId")
    List<Appointment> findByPatientId(Long patientId);
}
