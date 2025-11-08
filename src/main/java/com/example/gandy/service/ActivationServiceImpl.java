package com.example.gandy.service;
import com.example.gandy.entity.Activation;
import com.example.gandy.repo.ActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ActivationServiceImpl  {
    @Autowired
     ActivationRepository repository;

    public void addObject(Activation object) {
        repository.save(object);
    }

    public void updateObject(Activation object) {
        repository.save(object);
    }

    public Optional<Activation> getByIdObject(Long id) {
        return repository.findById(id);
    }

    public Activation confirmOtp(String mobile, String otp) {
        return repository.confirmOtp(mobile,otp);
    }

    public Collection<Activation> getAllObjects() {
        return repository.findAll();
    }

    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
