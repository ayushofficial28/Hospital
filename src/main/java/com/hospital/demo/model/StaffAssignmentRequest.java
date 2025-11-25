package com.hospital.demo.model;

import lombok.Data;

@Data
public class StaffAssignmentRequest {
    private int staffId;
    private int roomId;

}