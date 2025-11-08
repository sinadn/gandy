package com.example.gandy.service.productImage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductImageService<T> {
    void addObject(T object);

    void updateObject(T object);

    Optional<T> getByIdObject(Long id);

    List<T> findByProductId(Long id);

    Collection<T> getAllObjects();

    void deleteByIdObject(Long id);
}
