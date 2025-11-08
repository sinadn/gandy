package com.example.gandy.repo;

import com.example.gandy.entity.ERole;
import com.example.gandy.entity.EShipmentStatus;
import com.example.gandy.entity.Role;
import com.example.gandy.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long> {
    Optional<ShipmentStatus> findByName(EShipmentStatus name);
}