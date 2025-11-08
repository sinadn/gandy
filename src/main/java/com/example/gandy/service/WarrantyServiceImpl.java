package com.example.gandy.service;

import com.example.gandy.entity.Guarantee;
import com.example.gandy.entity.Warranty;
import com.example.gandy.repo.GuaranteeRepository;
import com.example.gandy.repo.WarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarrantyServiceImpl {
    @Autowired
    WarrantyRepository WarrantyRepository;

    public void createObjects(Warranty warranty) {
        WarrantyRepository.save(warranty);
    }
    public List<Warranty> getAll() {
        return WarrantyRepository.findAll();
    }

    public Page<Warranty> findAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return WarrantyRepository.findAll(paging);
    }


    public void deleteObjects(int id) {
        WarrantyRepository.deleteById(id);
    }
    public Warranty findWarranty(int id) {
        return WarrantyRepository.findWarrantyById(id);
    }
    public List<Warranty> findWarrantyByProductId(int id) {
        return WarrantyRepository.findWarrantyByProductId(id);
    }

    public List<Warranty> findWarrantyByproductName(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return WarrantyRepository.findWarrantyByproductName(name,paging);
    }





}
