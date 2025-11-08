package com.example.gandy.repo;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.FavoriteProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    FavoriteProduct findFavoriteProductByProductCountIdAndUsersId(long id , long userId);

    List<FavoriteProduct> findFavoriteProductByUsersId(long id);

    void deleteByUsersIdAndId(long userId , long id);

}