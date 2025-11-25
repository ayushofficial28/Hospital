package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {}