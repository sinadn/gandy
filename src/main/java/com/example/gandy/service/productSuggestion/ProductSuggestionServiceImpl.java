package com.example.gandy.service.productSuggestion;

import com.example.gandy.entity.ProductSuggestion;
import com.example.gandy.entity.ProductType;
import com.example.gandy.payload.request.PsugRequest;
import com.example.gandy.repo.ProductSuggestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductSuggestionServiceImpl {
    @Autowired
    private ProductSuggestionRepository repository;


    public List<ProductSuggestion> getpsug() {
        Pageable paging = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "id"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
        return repository.getpsug(today,paging);
    }


    public ProductSuggestion findObject(long id) {
        return repository.findProductSuggestionsById(id);
    }


    public void addObject(ProductSuggestion object) {
        repository.save(object);
        repository.saveAndFlush(object);
    }

    public void updateObject(ProductSuggestion object) {
        repository.save(object);
    }

    public Optional<ProductSuggestion> getByIdObject(Long id) {
        return repository.findById(id);
    }

    public Page<ProductSuggestion> getAllObjects(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }


    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
