package com.hospital.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.demo.model.Admission;
import com.hospital.demo.model.Patient;
import com.hospital.demo.model.TreatmentRecord;
import com.hospital.demo.repo.PatientRepository;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // 🔹 1. Register Patient
    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(saved);
    }

    // 🔹 2. Update Patient
    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @RequestBody Patient updatedData) {
        Optional<Patient> optional = patientRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Patient patient = optional.get();
        patient.setFirstName(updatedData.getFirstName());
        patient.setLastName(updatedData.getLastName());
        patient.setDateOfBirth(updatedData.getDateOfBirth());
        patient.setGender(updatedData.getGender());
        patient.setContactNumber(updatedData.getContactNumber());
        patient.setBloodGroup(updatedData.getBloodGroup());

        Patient updated = patientRepository.save(patient);
        return ResponseEntity.ok(updated);
    }

    // 🔹 3. Get Admission History
    @GetMapping("/{id}/admissions")
    public ResponseEntity<List<Admission>> getAdmissionHistory(@PathVariable int id) {
        Optional<Patient> optional = patientRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        List<Admission> admissions = optional.get().getAdmissions();
        return ResponseEntity.ok(admissions);
    }

    // 🔹 4. Get Treatment History
    @GetMapping("/{id}/treatments")
    public ResponseEntity<List<TreatmentRecord>> getTreatmentHistory(@PathVariable int id) {
        Optional<Patient> optional = patientRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        List<TreatmentRecord> treatments = optional.get().getTreatmentRecords();
        return ResponseEntity.ok(treatments);
    }

    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable int id) {
        return patientRepository.findById(id);
    }

}