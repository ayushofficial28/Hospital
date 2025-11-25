package com.hospital.demo.controllers;

import java.time.LocalDateTime;
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

import com.hospital.demo.model.Admission;
import com.hospital.demo.model.Doctor;
import com.hospital.demo.model.Patient;
import com.hospital.demo.model.TreatmentRecord;
import com.hospital.demo.model.TreatmentRecordRequest;
import com.hospital.demo.repo.AdmissionRepository;
import com.hospital.demo.repo.DoctorRepository;
import com.hospital.demo.repo.TreatmentRecordRepository;

@RestController
@RequestMapping("/treatments")
public class TreatmentRecordController {

    @Autowired
    private TreatmentRecordRepository treatmentRepository;

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // 🔹 1. Create Treatment Record (patient derived from admission)
    @PostMapping("/create")
    public ResponseEntity<?> createRecord(@RequestBody TreatmentRecordRequest request) {
        Optional<Admission> admissionOpt = admissionRepository.findById(request.getAdmissionId());
        Optional<Doctor> doctorOpt = doctorRepository.findById(request.getDoctorId());

        if (admissionOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admission not found");
        if (doctorOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");

        Admission admission = admissionOpt.get();
        Patient patient = admission.getPatient(); // 🔥 patient derived from admission

        TreatmentRecord record = new TreatmentRecord();
        record.setAdmission(admission);
        record.setDoctor(doctorOpt.get());
        record.setPatient(patient);
        record.setDisease(request.getDisease());
        record.setTreatment(request.getTreatment());
        record.setDate(LocalDateTime.now());

        TreatmentRecord saved = treatmentRepository.save(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 🔄 2. Update Treatment Record
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable int id, @RequestBody TreatmentRecordRequest request) {
        Optional<TreatmentRecord> recordOpt = treatmentRepository.findById(id);
        if (recordOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treatment record not found");

        TreatmentRecord record = recordOpt.get();
        record.setDisease(request.getDisease());
        record.setTreatment(request.getTreatment());
        record.setDate(LocalDateTime.now());

        TreatmentRecord updated = treatmentRepository.save(record);
        return ResponseEntity.ok(updated);
    }

    // 📋 3. View Treatment Record by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecord(@PathVariable int id) {
        Optional<TreatmentRecord> optional = treatmentRepository.findById(id);

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get()); // ✅ returns ResponseEntity<TreatmentRecord>
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treatment record not found"); // ✅ returns ResponseEntity<String>
        }
    }
}