package com.hospital.demo.model;

import lombok.Data;

@Data
public class TreatmentRecordRequest {
    private int admissionId;
    private int doctorId;
    private String disease;
    private String treatment;

}