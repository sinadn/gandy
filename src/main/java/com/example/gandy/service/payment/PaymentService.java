package com.example.gandy.service.payment;

import java.util.Collection;
import java.util.Optional;

public interface PaymentService<T> {
    void addObject(T object);

    void updateObject(T object);

    Optional<T> getByIdObject(Long id);

    Collection<T> getAllObjects();

    void deleteByIdObject(Long id);
}
