package com.pmarko09.medical_clinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private Set<Long> appointmentIds;

}
