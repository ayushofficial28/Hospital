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

import com.hospital.demo.model.Staff;
import com.hospital.demo.model.StaffAssignment;
import com.hospital.demo.repo.StaffRepository;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    // 🔹 1. Register Staff
    @PostMapping("/register")
    public ResponseEntity<Staff> registerStaff(@RequestBody Staff staff) {
        Staff saved = staffRepository.save(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // 🔹 2. Update Staff
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable int id, @RequestBody Staff updatedData) {
        Optional<Staff> optional = staffRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");

        Staff staff = optional.get();
        staff.setFirstName(updatedData.getFirstName());
        staff.setLastName(updatedData.getLastName());
        staff.setStaffType(updatedData.getStaffType());
        staff.setContactNumber(updatedData.getContactNumber());

        Staff updated = staffRepository.save(staff);
        return ResponseEntity.ok(updated);
    }

    // 🔹 3. View Assignments for Staff
    @GetMapping("/{id}/assignments")
    public ResponseEntity<?> getAssignmentsForStaff(@PathVariable int id) {
        Optional<Staff> optional = staffRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");

        List<StaffAssignment> assignments = optional.get().getAssignments();
        return ResponseEntity.ok(assignments);
    }
}