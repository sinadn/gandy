package com.example.gandy.service;

import com.example.gandy.entity.AttributeType;
import com.example.gandy.entity.Users;
import com.example.gandy.exception.ObjectException;
import com.example.gandy.repo.AttributeTypeRepository;
import com.example.gandy.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeTypeServiceImpl {
    @Autowired
    AttributeTypeRepository attributeTypeRepository;

    public void createObjects(AttributeType attributeType) {
        attributeTypeRepository.save(attributeType);
    }


    public List<AttributeType> getAttributeTypeByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  attributeTypeRepository.findAttributeTypeByName(name , paging);
    }

    public Page<AttributeType> getAllAttributeType(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return attributeTypeRepository.findAll(paging);
    }


    public void deleteByIdObject(Long id) {
        attributeTypeRepository.deleteById(id);
    }



    public AttributeType findObject(long id) {
        return attributeTypeRepository.findById(id)
                .orElseThrow(() -> new ObjectException("There is not any AttributeType by this id"));
    }

}
