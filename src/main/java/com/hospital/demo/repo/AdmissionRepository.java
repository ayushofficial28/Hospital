package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Integer> {
    
}
