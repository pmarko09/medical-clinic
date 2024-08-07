package com.pmarko09.medical_clinic.repository;

import com.pmarko09.medical_clinic.exception.SameDoctorEmailException;
import com.pmarko09.medical_clinic.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {

    private final List<Doctor> doctors;

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public Doctor addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctor;
    }

    public Optional<Doctor> getDoctor(String email) {
        return doctors.stream()
                .filter(doctor -> doctor.getEmail().equals(email))
                .findFirst();
    }

    public boolean emailDoctorExist (String email) {
        return getDoctor(email).isPresent();
    }


    public Optional<Doctor> deleteDoctor(String email) {
        Optional<Doctor> doctorToBeRemoved = getDoctor(email);

        doctorToBeRemoved.ifPresent(doctors::remove);
        return doctorToBeRemoved;
    }

    public Optional<Doctor> editDoctor(String email, Doctor newDoctor) {
        if (emailDoctorExist(email)) {
            throw new SameDoctorEmailException(email);
        }
        return getDoctor(email).map(doctor -> {
            doctor.setFirstName(newDoctor.getFirstName());
            doctor.setLastName(newDoctor.getLastName());
            doctor.setEmail(newDoctor.getEmail());
            doctor.setPassword(newDoctor.getPassword());
            doctor.setSpecialization(newDoctor.getSpecialization());
            return doctor;
        });
    }

    public Optional<Doctor> changeDoctorPassword(String email, String newPassword) {
        if (emailDoctorExist(email)) {
            throw new SameDoctorEmailException(email);
        }
        Optional<Doctor> doctor = getDoctor(email);
        doctor.ifPresent(d -> d.setPassword(newPassword));
        return doctor;
    }
}
