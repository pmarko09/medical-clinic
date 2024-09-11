package com.pmarko09.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {

    private Long id;
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    private Set<Long> doctorsIds;

}
