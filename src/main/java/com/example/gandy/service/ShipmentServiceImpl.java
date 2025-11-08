package com.example.gandy.service;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Shipment;
import com.example.gandy.entity.Warranty;
import com.example.gandy.repo.RegWarrantyRepository;
import com.example.gandy.repo.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl {
    @Autowired
    ShipmentRepository shipmentRepository;

    public void createObjects(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    public List<Shipment> getAll() {
        return shipmentRepository.findAll();
    }

    public Page<Shipment> findAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return shipmentRepository.findAll(paging);
    }

    public Shipment findShipment(int id) {
        return shipmentRepository.findShipmentById(id);
    }

    public List<Shipment> findShipmentByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  shipmentRepository.findShipmentByWords(name , paging);
    }

    public void deleteObjects(int id) {
        shipmentRepository.deleteById(id);
    }






}
