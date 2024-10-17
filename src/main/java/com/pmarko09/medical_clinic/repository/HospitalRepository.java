package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.model.model.Hospital;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Optional<Hospital> findByName(String name);

    @Query("SELECT h FROM Hospital h")
    List<Hospital> findAllHospitals(Pageable pageable);
}
