package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {}
