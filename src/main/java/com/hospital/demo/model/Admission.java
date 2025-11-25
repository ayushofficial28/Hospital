package com.hospital.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admissionID;

    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patientID")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorID")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "departmentID")
    private Department department;

    @OneToMany(mappedBy = "admission")
    @JsonIgnore
    private List<TreatmentRecord> treatmentRecords;
}