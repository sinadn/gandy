package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Product;
import com.example.gandy.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findProductById(long id);

    @Query("SELECT p FROM Product p   WHERE p.name like %:name%  ")
    List<Product> findProductByWords(@Param("name") String name , Pageable pageable);


    @Query("SELECT p FROM Product p left join fetch p.productInfos")
    Page<Product> getAll(Pageable pageable);




//    @Query("SELECT distinct p.id , p.description , p.name , i.price FROM Product p left join ProductCount i on p.id=i.product.id WHERE p.id=?1 ")
//    List<Object> findProductById(long id);
//
//    @Query("SELECT distinct p.id , p.description , p.name , i.img  , c.price FROM Product p left join ProductCount c on p.id=c.product.id left join ProductImage i on p.id=i.product.id WHERE p.brand.id=?1 ")
//    List<Object> findProductByBrand_Id(int id);
//
//    @Query("SELECT p FROM Product p WHERE p.productType.id=?1 ")
//    List<Product> findProductByProductType(int id);
//
//    @Query("SELECT p.id , p.description , p.name , i.img FROM Product p left join ProductImage i on p.id=i.product.id  WHERE p.name like ?1%")
//    List<Object> findProductByWords(String name);


//    p.description , p.name , i.img
//    WHERE p.name like ?1%


}