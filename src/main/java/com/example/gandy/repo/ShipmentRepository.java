package com.example.gandy.repo;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Shipment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    Shipment findShipmentById(int id);

    @Query("SELECT s FROM Shipment s WHERE s.name like %:name%  ")
    List<Shipment> findShipmentByWords(@Param("name") String name , Pageable pageable);


}