package com.example.gandy.repo;

import com.example.gandy.entity.WebInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebInfoRepository extends JpaRepository<WebInfo, Long> {
    WebInfo getWebInfoById(long id);

}