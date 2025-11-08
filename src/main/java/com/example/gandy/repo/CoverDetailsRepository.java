package com.example.gandy.repo;

import com.example.gandy.entity.CoverDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverDetailsRepository extends JpaRepository<CoverDetails, Long> {

}