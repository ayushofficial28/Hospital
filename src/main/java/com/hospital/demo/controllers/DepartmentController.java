package com.hospital.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.demo.model.Admission;
import com.hospital.demo.model.Department;
import com.hospital.demo.model.Doctor;
import com.hospital.demo.repo.DepartmentRepository;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        return departmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    // 🔹 Show all doctors in a department
    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable int id) {
        Optional<Department> deptOpt = departmentRepository.findById(id);
        if (deptOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Doctor> doctors = deptOpt.get().getDoctors(); // uses mappedBy
        return ResponseEntity.ok(doctors);
    }

    // 🔹 Show all admissions in a department
    @GetMapping("/{id}/admissions")
    public ResponseEntity<List<Admission>> getAdmissionsByDepartment(@PathVariable int id) {
        Optional<Department> deptOpt = departmentRepository.findById(id);
        if (deptOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Admission> admissions = deptOpt.get().getAdmissions();
        return ResponseEntity.ok(admissions);
    }
}