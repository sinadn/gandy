package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Cover;
import com.example.gandy.entity.TagAmountBox;
import com.example.gandy.payload.request.CategoryRequest;
import com.example.gandy.repo.CategoryRepository;
import com.example.gandy.repo.TagAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagAmountBoxServiceImpl {
    @Autowired
    TagAmountRepository repository;


    public void createObjects(TagAmountBox objects) {
        repository.save(objects);
    }


    public void deleteObjects(long id) {
        repository.deleteById(id);
    }


    public TagAmountBox getById(Long id) {
        return repository.getTagAmountBoxById(id);
    }



    public List<TagAmountBox> getAll() {
        return repository.findAll();
    }


    public Integer countTagAmountBoxByBox(int id) {
        return repository.countTagAmountBoxByBox(id);
    }


    public void deleteCoverByImgName(String name) {
        TagAmountBox tagAmountBox = repository.findTagAmountBoxByImage(name);
        tagAmountBox.setImage("");
        repository.save(tagAmountBox);
//        coverRepository.deleteCoverByImage(name);
    }


}
