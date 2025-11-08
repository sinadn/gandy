package com.example.gandy.service;

import com.example.gandy.entity.Discount;
import com.example.gandy.entity.ProductSuggestion;
import com.example.gandy.entity.ProductType;
import com.example.gandy.repo.DiscountRepository;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DiscountServiceImpl {
    @Autowired
    private DiscountRepository repository;


//    public Page<Discount> getDiscounts() {
//        Pageable paging = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "id"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String formatDateTime = LocalDateTime.now().format(formatter);
//        LocalDateTime today = LocalDateTime.parse(formatDateTime, formatter);
//        return repository.findAll(today,paging);
//    }


    public Discount findObject(long id) {
        return repository.findDiscountById(id);
    }


    public void addObject(Discount object) {
        repository.save(object);
    }

    public List<Discount> getDiscountByWords(String num) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  repository.getDiscountByNumber(num , paging);
    }

    public Page<Discount> getAllObjects(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }


    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
