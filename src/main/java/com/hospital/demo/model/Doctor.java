package com.hospital.demo.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorID;

    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;
    
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "departmentID")
    private Department department;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Admission> admissions;

    
    
}