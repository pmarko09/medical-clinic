package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "HOSPITALS", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;

    @ManyToMany(mappedBy = "hospitals")
    private Set<Doctor> doctors;

    public static void update(Hospital hospital, Hospital editedHospital) {
        hospital.setName(editedHospital.getName());
        hospital.setCity(editedHospital.getCity());
        hospital.setDoctors(editedHospital.getDoctors());
        hospital.setBuildingNumber(editedHospital.getBuildingNumber());
        hospital.setStreet(editedHospital.getStreet());
        hospital.setPostalCode(editedHospital.getPostalCode());
    }
}
