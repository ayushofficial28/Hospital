package com.hospital.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.demo.model.Room;
import com.hospital.demo.model.Staff;
import com.hospital.demo.model.StaffAssignment;
import com.hospital.demo.model.StaffAssignmentRequest;
import com.hospital.demo.repo.RoomRepository;
import com.hospital.demo.repo.StaffAssignmentRepository;
import com.hospital.demo.repo.StaffRepository;

@RestController
@RequestMapping("/assignments")
public class StaffAssignmentController {

    @Autowired
    private StaffAssignmentRepository assignmentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoomRepository roomRepository;

    // 🔹 1. Create Assignment
    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@RequestBody StaffAssignmentRequest request) {
        Optional<Staff> staffOpt = staffRepository.findById(request.getStaffId());
        Optional<Room> roomOpt = roomRepository.findById(request.getRoomId());

        if (staffOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");
        if (roomOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");

        StaffAssignment assignment = new StaffAssignment();
        assignment.setStaff(staffOpt.get());
        assignment.setRoom(roomOpt.get());

        StaffAssignment saved = assignmentRepository.save(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 🔄 2. Update Assignment
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable int id, @RequestBody StaffAssignmentRequest request) {
        Optional<StaffAssignment> assignmentOpt = assignmentRepository.findById(id);
        Optional<Staff> staffOpt = staffRepository.findById(request.getStaffId());
        Optional<Room> roomOpt = roomRepository.findById(request.getRoomId());

        if (assignmentOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found");
        if (staffOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");
        if (roomOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");

        StaffAssignment assignment = assignmentOpt.get();
        assignment.setStaff(staffOpt.get());
        assignment.setRoom(roomOpt.get());

        StaffAssignment updated = assignmentRepository.save(assignment);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public List<StaffAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

}