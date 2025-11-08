package com.example.gandy.service.webInfo;

import com.example.gandy.entity.WebInfo;

import java.util.Optional;

public interface WebInfoService<T> {
    void addObject(T object);

    void updateObject(T object);

    WebInfo getByIdObject(Long id);

    Optional getAllObjects();

    void deleteByIdObject(Long id);
}
