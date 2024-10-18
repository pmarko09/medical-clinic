package com.pmarko09.medical_clinic.model.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "APPOINTMENTS")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime appStartTime;
    private LocalDateTime appFinishTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment other)) {
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
        return "Appointment{" +
                "id=" + id +
                ", appStartTime=" + appStartTime +
                ", appEndTime=" + appFinishTime +
                ", doctor=" + doctor.getId() +
                ", patient=" + patient.getId() +
                '}';
    }
}
