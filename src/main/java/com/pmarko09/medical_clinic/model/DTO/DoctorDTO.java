package com.pmarko09.medical_clinic.model.DTO;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private Set<Long> hospitalsIds;

}
