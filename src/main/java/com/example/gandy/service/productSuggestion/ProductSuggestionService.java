package com.example.gandy.service.productSuggestion;

import java.util.Collection;
import java.util.Optional;

public interface ProductSuggestionService<T> {
    void addObject(T object);

    void updateObject(T object);

    Optional<T> getByIdObject(Long id);

    Collection<T> getAllObjects();

    void deleteByIdObject(Long id);
}
