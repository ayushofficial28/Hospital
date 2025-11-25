package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.StaffAssignment;

public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, Integer> {}