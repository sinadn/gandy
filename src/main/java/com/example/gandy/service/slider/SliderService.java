package com.example.gandy.service.slider;

import java.util.Collection;
import java.util.Optional;

public interface SliderService<T> {
    void addObject(T object);

    void updateObject(T object);

    Optional<T> getByIdObject(Long id);

    Collection<T> getAllObjects();

    void deleteByIdObject(Long id);
}
