package com.example.gandy.service.footerMenu;

import com.example.gandy.entity.FooterMenu;
import com.example.gandy.entity.MainWare;
import com.example.gandy.entity.ProductType;
import com.example.gandy.repo.FooterMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FooterMenuServiceImpl  {
    @Autowired
    private FooterMenuRepository footerMenuRepository;


    public void addObject(FooterMenu object) {
        footerMenuRepository.save(object);
    }

    public void updateObject(FooterMenu object) {
        footerMenuRepository.save(object);
    }

    public FooterMenu getByIdObject(Long id) {
        return footerMenuRepository.findFooterMenuById(id);
    }

    public Page<FooterMenu> getAllObjects() {
        Pageable paging = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "id"));
        return footerMenuRepository.findAll(paging);
    }

    public void deleteByIdObject(Long id) {
        footerMenuRepository.deleteById(id);
    }

    public List<FooterMenu> getFooterMenuByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  footerMenuRepository.getFooterMenuByWords(name , paging);
    }



}
