package com.pmarko09.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

}
