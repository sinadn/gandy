package com.example.gandy.service;

import com.example.gandy.entity.RegWarranty;
import com.example.gandy.entity.Users;
import com.example.gandy.entity.Warranty;
import com.example.gandy.repo.RegWarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegWarrantyServiceImpl {
    @Autowired
    RegWarrantyRepository regWarrantyRepository;

    public void createObjects(RegWarranty regWarranty) {
        regWarrantyRepository.save(regWarranty);
    }
    public List<RegWarranty> getAll() {
        return regWarrantyRepository.findAll();
    }

    public List<RegWarranty> findRegWarrantyByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  regWarrantyRepository.findRegWarrantyByWords(name , paging);

    }



    public Page<RegWarranty> findAll(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return regWarrantyRepository.findAll(paging);
    }



    public void deleteObjects(int id) {
        regWarrantyRepository.deleteById(id);
    }
    public RegWarranty findWarranty(int id) {
        return regWarrantyRepository.findRegWarrantyById(id);
    }





}
