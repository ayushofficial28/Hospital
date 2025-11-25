package com.hospital.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {}
