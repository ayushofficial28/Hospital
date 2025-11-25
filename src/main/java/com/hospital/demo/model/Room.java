package com.hospital.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Room {
    @Id
    private int roomID;
    
    private String roomType;
    @JsonIgnore
    private boolean isOccupied;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Admission> admissions;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<StaffAssignment> staffAssignments;

    public void allocateRoom() {
        this.isOccupied = true;
    }

    public void deallocateRoom() {
        this.isOccupied = false;
    }
}