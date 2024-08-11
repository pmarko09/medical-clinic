package com.pmarko09.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;

}
