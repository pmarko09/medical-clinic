package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.model.model.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    @Query("SELECT d FROM Doctor d")
    List<Doctor> findAllDoctors(Pageable pageable);
}
