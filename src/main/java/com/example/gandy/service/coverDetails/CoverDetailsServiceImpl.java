package com.example.gandy.service.coverDetails;

import com.example.gandy.entity.CoverDetails;
import com.example.gandy.entity.WebInfo;
import com.example.gandy.repo.CoverDetailsRepository;
import com.example.gandy.repo.WebInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class CoverDetailsServiceImpl implements CoverDetailsService<CoverDetails> {
    @Autowired
    private CoverDetailsRepository repository;


    @Override
    public void addObject(CoverDetails object) {
        repository.save(object);
    }

    @Override
    public void updateObject(CoverDetails object) {
        repository.save(object);
    }


    @Override
    public Optional<CoverDetails> getByIdObject(Long id) {
        return repository.findById(id);
    }

    @Override
    public Collection<CoverDetails> getAllObjects() {
        return repository.findAll();
    }

    @Override
    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
