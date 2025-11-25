package com.hospital.demo.model;

import lombok.Data;

@Data
public class PatientRegisterDTO {
    private Patient patient;
    private Admission admission;
    
}
