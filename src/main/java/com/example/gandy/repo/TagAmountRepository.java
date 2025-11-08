package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Cover;
import com.example.gandy.entity.TagAmountBox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagAmountRepository extends JpaRepository<TagAmountBox, Long> {
    TagAmountBox getTagAmountBoxById(long id);
    Integer countTagAmountBoxByBox(int id);
    TagAmountBox findTagAmountBoxByImage(String image);

}