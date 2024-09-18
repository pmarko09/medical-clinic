package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HOSPITALS", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private Set<Doctor> doctors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital other)) {
            return false;
        }

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Hospital{" +
                " id=" + id +
                ", name=" + name +
                ", city=" + city +
                ", postalCode=" + postalCode +
                ", street=" + street +
                ", buildingNumber=" + buildingNumber +
                ", doctors=" + doctors.stream().map(Doctor::getId).toList();
    }

    public static void update(Hospital hospital, Hospital editedHospital) {
        hospital.setName(editedHospital.getName());
        hospital.setCity(editedHospital.getCity());
        hospital.setDoctors(editedHospital.getDoctors());
        hospital.setBuildingNumber(editedHospital.getBuildingNumber());
        hospital.setStreet(editedHospital.getStreet());
        hospital.setPostalCode(editedHospital.getPostalCode());
    }
}
