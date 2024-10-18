package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.model.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllApp(Pageable pageable);
}
