package com.hospital.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.hospital.demo.model.User;
import com.hospital.demo.repo.DoctorRepository;
import com.hospital.demo.repo.UserRepository;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    //1. Register Doctor
    @Autowired
private PasswordEncoder passwordEncoder; // 🔐 Injected BCrypt encoder

@PostMapping("/register")
public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor) {
    Doctor saved = doctorRepository.save(doctor);

    //Username = "DOCT" + doctorID
    String username = "DOCT" + saved.getDoctorID();

    //Password = first 4 of firstName + last 4 of contactNumber
    String firstPart = doctor.getFirstName().length() >= 4
        ? doctor.getFirstName().substring(0, 4)
        : doctor.getFirstName();
    String contact = doctor.getContactNumber();
    String lastPart = contact.length() >= 4
        ? contact.substring(contact.length() - 4)
        : contact;
    String rawPassword = firstPart + lastPart;

    //Hash password using BCrypt
    String hashedPassword = passwordEncoder.encode(rawPassword);

    //Create User
    User user = new User();
    user.setUsername(username);       // this is the @Id
    user.setPassword(hashedPassword); // securely stored

    userRepository.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update Doctor Profile
    @PutMapping("/{id}/update")
    public ResponseEntity<Doctor> updateDoctorProfile(@PathVariable int id, @RequestBody Doctor updatedData) {
        Optional<Doctor> optional = doctorRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Doctor doctor = optional.get();
        doctor.setFirstName(updatedData.getFirstName());
        doctor.setLastName(updatedData.getLastName());
        doctor.setSpecialization(updatedData.getSpecialization());
        doctor.setContactNumber(updatedData.getContactNumber());
        doctor.setDepartment(updatedData.getDepartment()); 

        Doctor updated = doctorRepository.save(doctor);
        return ResponseEntity.ok(updated);
    }

    // Get Patients Treated by Doctor
    @GetMapping("/{id}/patients")
    public ResponseEntity<List<Patient>> getPatientsByDoctor(@PathVariable int id) {
        Optional<Doctor> optional = doctorRepository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        List<Patient> patients = optional.get().getAdmissions().stream()
                .map(Admission::getPatient)
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}/admissions")
    public ResponseEntity<List<Admission>> getAdmissionsForDoctor(@PathVariable int id) {
        return doctorRepository.findById(id)
                .map(doctor -> ResponseEntity.ok(doctor.getAdmissions()))
                .orElse(ResponseEntity.notFound().build());
    }

}