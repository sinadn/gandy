package com.example.gandy.service.footerSubMenu;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.FooterMenu;
import com.example.gandy.entity.FooterSubMenu;
import com.example.gandy.repo.FooterMenuRepository;
import com.example.gandy.repo.FooterSubMenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FooterSubMenuServiceImpl {
    @Autowired
     FooterSubMenuRepository repository;


    public void addObject(FooterSubMenu object) {
        repository.save(object);
    }

    public void updateObject(FooterSubMenu object) {
        repository.save(object);
    }

    public Optional<FooterSubMenu> getByIdObject(Long id) {
        return repository.findById(id);
    }

    public Page<FooterSubMenu> getAllObjects() {
        Pageable paging = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(paging);
    }

    public FooterSubMenu getFooterSubMenuByName(String name) {
        return  repository.findFooterSubMenuByName(name);
    }

    public FooterSubMenu findFooterSubMenuByUrl(String name) {
        return  repository.findFooterSubMenuByUrl(name);
    }




    public void deleteByIdObject(Long id) {
        repository.deleteById(id);
    }
}
