package com.example.gandy.service;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.Cover;
import com.example.gandy.repo.ArticleRepository;
import com.example.gandy.repo.CoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverServiceImpl {
    @Autowired
    CoverRepository coverRepository;

    public void createObjects(Cover cover) {
        coverRepository.save(cover);
    }

    public List<Cover> findAll() {
        return coverRepository.findAll();
    }

    public Page<Cover> findAllByPagination(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return coverRepository.findAll(paging);
    }
    public Cover findCover(int id) {
        return coverRepository.findByCoverId(id);
    }
    public List<Cover> findCoversByPosition(int position) {
        return coverRepository.findCoversByPosition(position);
    }
    public void deleteObjects(int id) {
        coverRepository.deleteById(id);
    }

    public void deleteCoverByImgName(String name) {
        Cover cover = coverRepository.findCoversByImage(name);
        cover.setImage("");
        coverRepository.save(cover);
//        coverRepository.deleteCoverByImage(name);
    }




}
