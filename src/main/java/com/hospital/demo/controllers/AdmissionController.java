package com.hospital.demo.controllers;

import java.time.LocalDateTime;
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
import com.hospital.demo.model.Department;
import com.hospital.demo.model.Doctor;
import com.hospital.demo.model.Patient;
import com.hospital.demo.model.PatientRegisterDTO;
import com.hospital.demo.model.Room;
import com.hospital.demo.model.TreatmentRecord;
import com.hospital.demo.repo.AdmissionRepository;
import com.hospital.demo.repo.DepartmentRepository;
import com.hospital.demo.repo.DoctorRepository;
import com.hospital.demo.repo.PatientRepository;
import com.hospital.demo.repo.RoomRepository;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    //Admit Patient
    @PostMapping("/admit")
public ResponseEntity<Admission> admitPatient(@RequestBody PatientRegisterDTO request) {
    Patient patient = request.getPatient();
    Admission admission = request.getAdmission();
    Patient savedPatient;
    if (patient.getPatientID() != 0 && patientRepository.existsById(patient.getPatientID())) {
        savedPatient = patientRepository.findById(patient.getPatientID()).get();
    } else {
        savedPatient = patientRepository.save(patient); // new patient
    }


    // Validate and fetch foreign keys
    Optional<Doctor> doctorOpt = doctorRepository.findById(admission.getDoctor().getDoctorID());
    Optional<Room> roomOpt = roomRepository.findById(admission.getRoom().getRoomID());
    Optional<Department> deptOpt = departmentRepository.findById(admission.getDepartment().getDepartmentID());

    if (doctorOpt.isEmpty() || roomOpt.isEmpty() || deptOpt.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    // Set admission fields
    admission.setPatient(savedPatient);
    admission.setDoctor(doctorOpt.get());
    admission.setRoom(roomOpt.get());
    admission.setDepartment(deptOpt.get());
    admission.setAdmissionDate(LocalDateTime.now());
    roomOpt.get().allocateRoom();


    Admission savedAdmission = admissionRepository.save(admission);
    return ResponseEntity.ok(savedAdmission);
}

    // Discharge Patient
    @PutMapping("/discharge/{id}")
    public ResponseEntity<Admission> dischargePatient(@PathVariable int id) {
        Optional<Admission> optional = admissionRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Admission admission = optional.get();
        admission.setDischargeDate(LocalDateTime.now());
        Optional<Room> roomOpt = roomRepository.findById(admission.getRoom().getRoomID());
        roomOpt.get().deallocateRoom();

        Admission updated = admissionRepository.save(admission);
        return ResponseEntity.ok(updated);
    }

    //Get Admission Details
    @GetMapping("/{id}")
    public ResponseEntity<Admission> getAdmissionDetails(@PathVariable int id) {
        return admissionRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/treatments")
    public ResponseEntity<List<TreatmentRecord>> getTreatmentRecords(@PathVariable int id) {
        return admissionRepository.findById(id)
                .map(admission -> ResponseEntity.ok(admission.getTreatmentRecords()))
                .orElse(ResponseEntity.notFound().build());
    }

}