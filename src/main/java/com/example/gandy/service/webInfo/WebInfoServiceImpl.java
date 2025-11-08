package com.example.gandy.service.webInfo;

import com.example.gandy.entity.ProductCount;
import com.example.gandy.entity.WebInfo;
import com.example.gandy.repo.WebInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WebInfoServiceImpl implements WebInfoService<WebInfo> {
    @Autowired
    private WebInfoRepository repository;


    @Override
    public void addObject(WebInfo object) {
        repository.save(object);
    }

    @Override
    public void updateObject(WebInfo object) {
        repository.save(object);
    }


    @Override
    public WebInfo getByIdObject(Long id) {
        return repository.getWebInfoById(id);
    }

    @Override
    public Optional getAllObjects() {
        return Optional.empty();
    }


    public List<WebInfo> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
