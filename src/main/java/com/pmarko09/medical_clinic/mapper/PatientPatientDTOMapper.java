package com.pmarko09.medical_clinic.mapper;

import com.pmarko09.medical_clinic.model.Patient;
import com.pmarko09.medical_clinic.model.PatientDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PatientPatientDTOMapper {

    PatientDTO patientToPatientDTO(Patient patient);

    Patient patientDTOtoPatient(PatientDTO patientDTO);
}
