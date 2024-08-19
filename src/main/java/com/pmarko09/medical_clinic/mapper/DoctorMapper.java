package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.exception.PatientNotFoundException;
import com.pmarko09.medical_clinic.model.Doctor;
import com.pmarko09.medical_clinic.model.DoctorDTO;
import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.repository.PatientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    @Autowired
    public PatientRepository patientRepository;

    @Mapping(source = "patientList", target = "patients", qualifiedByName = "patientsListToEmails")
    public abstract DoctorDTO toDto(Doctor doctor);

    @Mapping(source = "patients", target = "patientList", qualifiedByName = "emailsToPatientList")
    public abstract Doctor toDoctor(DoctorDTO doctorDTO);

    @Named("patientsListToEmails")
    public List<String> patientsListToEmails(List<Patient> patients) {
        if (patients == null || patients.isEmpty()) {
            return Collections.emptyList();
        }
        return patients.stream()
                .map(Patient::getEmail)
                .toList();
    }

    @Named("emailsToPatientList")
    public List<Patient> emailsToPatientList(List<String> emails) {
        if (emails == null || emails.isEmpty()) {
            return Collections.emptyList();
        }
        return emails.stream()
                .map(email -> {
                    return patientRepository.getPatient(email)
                            .orElseThrow(() -> new PatientNotFoundException(email));
                })
                .collect(Collectors.toList());
    }

}
