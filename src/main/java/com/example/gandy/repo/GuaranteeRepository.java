package com.example.gandy.repo;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, Integer> {

    @Query("SELECT g FROM Guarantee g WHERE g.id=?1")
    Guarantee findGuaranteeById(int id);

}