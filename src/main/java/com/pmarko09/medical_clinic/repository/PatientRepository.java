package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.model.model.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    @Query("SELECT p FROM Patient p")
    List<Patient> findAllPatients(Pageable pageable);
}
