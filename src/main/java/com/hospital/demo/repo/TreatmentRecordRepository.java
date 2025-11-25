package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.TreatmentRecord;

public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, Integer> {}
