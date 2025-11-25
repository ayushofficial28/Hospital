package com.hospital.demo.model;

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
public class StaffAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentID;


    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staffID")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "roomID")
    private Room room;
}