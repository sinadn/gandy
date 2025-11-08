package com.example.gandy.service;

import com.example.gandy.entity.Cover;
import com.example.gandy.entity.FavoriteProduct;
import com.example.gandy.repo.CoverRepository;
import com.example.gandy.repo.FavoriteProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteProductImpl {
    @Autowired
    FavoriteProductRepository favoriteProductRepository;

    public void createObjects(FavoriteProduct favoriteProduct) {
        FavoriteProduct favoriteProduct1 = null;
        try {
            favoriteProduct1 = favoriteProductRepository.findFavoriteProductByProductCountIdAndUsersId(favoriteProduct.getProductCount().getId(),favoriteProduct.getUsers().getId());
        } catch (Exception e) {
            e.getMessage();
        }
        if (favoriteProduct1 == null) {
            favoriteProductRepository.save(favoriteProduct);
        }
    }

    public void deleteObjects(long id, long userId) {
        favoriteProductRepository.deleteByUsersIdAndId(userId, id);
    }

    public FavoriteProduct findFavoriteProduct(long id, long userId) {
        return favoriteProductRepository.findFavoriteProductByProductCountIdAndUsersId(id, userId);
    }

    public List<FavoriteProduct> findAll(long id) {
        return favoriteProductRepository.findFavoriteProductByUsersId(id);
    }

//    public FavoriteProduct getFavoriteProduct(long id) {
//        return favoriteProductRepository.findFavoriteProductByIdAndUsersId(id);
//    }


}
