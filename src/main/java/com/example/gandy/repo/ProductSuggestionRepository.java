package com.example.gandy.repo;

import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductSuggestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductSuggestionRepository extends JpaRepository<ProductSuggestion, Long> {

    @Query("SELECT p FROM ProductSuggestion p WHERE   p.create_at<=?1 AND p.expire_at>=?1")
    List<ProductSuggestion> getpsug(LocalDateTime today , Pageable limit);

    ProductSuggestion findProductSuggestionsById(long id);


}