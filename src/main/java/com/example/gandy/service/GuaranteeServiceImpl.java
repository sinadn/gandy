package com.example.gandy.service;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.Guarantee;
import com.example.gandy.repo.CoverRepository;
import com.example.gandy.repo.GuaranteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuaranteeServiceImpl {
    @Autowired
    GuaranteeRepository guaranteeRepository;

    public void createObjects(Guarantee guarantee) {
        guaranteeRepository.save(guarantee);
    }
    public List<Guarantee> findAll() {
        return guaranteeRepository.findAll();
    }
    public void deleteObjects(int id) {
        guaranteeRepository.deleteById(id);
    }
    public Guarantee findGuarantee(int id) {
        return guaranteeRepository.findGuaranteeById(id);
    }





}
