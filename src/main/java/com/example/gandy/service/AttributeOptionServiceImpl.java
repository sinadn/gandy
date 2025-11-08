package com.example.gandy.service;

import com.example.gandy.entity.AttributeOption;
import com.example.gandy.entity.AttributeType;
import com.example.gandy.entity.ProductCount;
import com.example.gandy.exception.ObjectException;
import com.example.gandy.repo.AttributeOptionRepository;
import com.example.gandy.repo.AttributeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeOptionServiceImpl {
    @Autowired
    AttributeOptionRepository attributeOptionRepository;

    @Autowired
    AttributeTypeServiceImpl attributeTypeService;

    @Autowired
    ProductTypeServiceImpl productTypeService;


    public void createObjects(AttributeOption attributeOption) {
        attributeOptionRepository.save(attributeOption);
    }


    public List<AttributeOption> getAttributeOptionByWords(String name,long id) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  attributeOptionRepository.findAttributeOptionByName(name , attributeTypeService.findObject(id).getId(), paging);
    }

    public List<AttributeOption> findAttributeOptions(long id) {
        List<Long> ptypes = new ArrayList<>();
            try {
                ptypes= productTypeService.findPTfromRoot(id);
            }catch (Exception e){
                e.getMessage();
            }
        return  attributeOptionRepository.findAttributeOptions(ptypes);
    }



    public List<AttributeOption> getAttributeOptionByAT(long id) {
        return  attributeOptionRepository.getAttributeOptionByAT(id);
    }

    public List<AttributeOption> searchAttributeOption(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  attributeOptionRepository.searchAttributeOption(name , paging);
    }



    public Page<AttributeOption> getAllAttributeOption(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return attributeOptionRepository.findAll(paging);
    }


    public void deleteByIdObject(Long id) {
        attributeOptionRepository.deleteById(id);
    }



    public AttributeOption findObject(long id) {
        return attributeOptionRepository.findById(id)
                .orElseThrow(() -> new ObjectException("There is not any AttributeOption by this id"));
    }








}
