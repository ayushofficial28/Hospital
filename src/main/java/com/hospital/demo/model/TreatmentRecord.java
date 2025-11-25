package com.hospital.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class TreatmentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    private LocalDateTime date;

    private String treatment;
    private String disease;

    @ManyToOne
    @JoinColumn(name = "admission_id", referencedColumnName = "admissionID")
    private Admission admission;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorID")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id", referencedColumnName = "patientID")
    private Patient patient;
}