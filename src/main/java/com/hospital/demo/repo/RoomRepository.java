package com.hospital.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.demo.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByIsOccupiedFalse();

}
