package com.hospital.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentID;

    private String departmentName;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Admission> admissions;
}