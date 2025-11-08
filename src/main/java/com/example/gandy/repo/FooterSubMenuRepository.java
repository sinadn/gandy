package com.example.gandy.repo;

import com.example.gandy.entity.Article;
import com.example.gandy.entity.FooterSubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooterSubMenuRepository extends JpaRepository<FooterSubMenu, Long> {

    FooterSubMenu findFooterSubMenuByName(String name);

    FooterSubMenu findFooterSubMenuByUrl(String name);



}