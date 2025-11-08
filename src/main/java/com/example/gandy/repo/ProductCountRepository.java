package com.example.gandy.repo;

import com.example.gandy.entity.ProductCount;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCount, Long> {
    @Query("SELECT p FROM ProductCount p WHERE  p.product.id=?1 order by p.id desc ")
    List<ProductCount> findProduct(long id);


    @Query("SELECT p FROM ProductCount p WHERE  p.product.name=?1 order by p.id desc ")
    List<ProductCount> findByProductName(String name);


    @Query("SELECT p.colorHex FROM ProductCount p WHERE  p.product.id=?1 order by p.id desc ")
    List<String> findProductColor(long id);


    @Query("SELECT p FROM ProductCount p where p.product.brand.id=?1 and p.main=?2 order by p.id desc ")
    List<ProductCount> findProductByBrand_Id(int id, int main ,Pageable paging);

//    @Query("SELECT p FROM ProductCount p WHERE p.main=:main and (p.product.productType.id IN (:numbers)  or" +
//            " p.product.id IN (SELECT e.product.id FROM ProductTag e where e.tag.id=:tag) or" +
//            " p.price >=:amount) or" +
//            " p.product.id IN (SELECT e.product.id FROM ProductConfig e where e.attributeOption.id=:atOp)")
//    List<ProductCount> advanceSearch(@Param("numbers") List<Long> numbers ,@Param("tag") int tag ,@Param("atOp") int atOp,@Param("amount") long amount  ,@Param("main") int main , Pageable pageable);


    @Query("SELECT p FROM ProductCount p where p.main=:main and p.product.productType.id IN :ptypes order by p.id desc ")
    List<ProductCount> getByProductType(@Param("main") int main ,@Param("ptypes") List<Long> ptypes ,Pageable paging);



    @Query("SELECT p FROM ProductCount p WHERE p.product.productType.id=?1 and p.product.brand.id=?2 and p.main=?3 order by p.id desc  ")
    List<ProductCount> findProductByProductTypeAndBrand(int ptId , int brandId ,int main , Pageable pageable);

    @Query("SELECT p FROM ProductCount p   WHERE (p.product.name like %:name% or p.product.description like %:name%)  and p.main=:main ")
    List<ProductCount> findProductByWords(@Param("name") String name , @Param("main") int main , Pageable pageable);

    @Query("SELECT p FROM ProductCount p   WHERE p.product.name like %:name% ")
    List<ProductCount> findProductCountByWordsForAdmin(@Param("name") String name , Pageable pageable);

    @Query("SELECT p FROM ProductCount p   WHERE p.product.AmazingOffer=?1 and p.main=?2 and p.count <> 0 order by p.id desc ")
    List<ProductCount> findAmazingOffer(int AmazingOffer , int main , Pageable pageable);

    @Query("SELECT p FROM ProductCount p WHERE p.product.productType.id IN :ptypes and p.main=:main and p.count <> 0 order by p.id desc ")
    List<ProductCount> findNewProduct(@Param("ptypes") List<Long> ptypes ,@Param("main") int main , Pageable pageable);

    @Query("SELECT p FROM ProductCount p WHERE p.price<=?1 and p.main=?2 order by p.id desc ")
    List<ProductCount> findByprice(int price, int main , Pageable pageable);
    @Query("SELECT p FROM ProductCount p WHERE p.id=?1 order by p.id desc ")
    ProductCount findByProdoctCountId(long id);


    @Query("SELECT p FROM ProductCount p WHERE p.price<=:price and p.product.productType.id IN :numbers and p.main=:main order by p.id desc ")
    List<ProductCount> findProductByAmount(@Param("price") long price , @Param("numbers") List<Long> numbers , @Param("main") int main , Pageable pageable);

//

    @Query("SELECT p FROM ProductCount p WHERE p.main=:main and p.product.productType.id IN :numbers and p.product.id IN (SELECT e.product.id FROM ProductTag e where e.tag.id=:tag)  order by p.id desc")
    List<ProductCount> findProductByTag(@Param("main") int main , @Param("numbers") List<Long> numbers ,@Param("tag") long tag , Pageable pageable);


    @Query("SELECT p FROM ProductCount p WHERE p.main=:main and p.product.productType.id IN :numbers and p.product.id IN (SELECT e.product.id FROM ProductConfig e where e.attributeOption.id=:attr)  order by p.id desc")
    List<ProductCount> findProductByAO(@Param("main") int main , @Param("numbers") List<Long> numbers  ,@Param("attr") long attr , Pageable pageable);


    /// these queries are for advance search ///

    @Query("SELECT p FROM ProductCount p WHERE p.main=:main and p.product.productType.id IN :numbers")
    List<ProductCount> findProductCountByProductType(@Param("main") int main , @Param("numbers") List<Long> numbers );


    @Query("SELECT p FROM ProductCount p WHERE p IN :list and p.main=:main and p.product.id IN (SELECT e.product.id FROM ProductTag e where e.tag.id=:tag) ")
    List<ProductCount> findProductCountByTag(@Param("main") int main , @Param("tag") int tag , @Param("list") List<ProductCount> list );


    @Query("SELECT p FROM ProductCount p WHERE p IN :list and p.main=:main and p.product.id IN (SELECT e.product.id FROM ProductConfig e where e.attributeOption.id IN :ptid) ")
    List<ProductCount> findProductCountByAtO(@Param("main") int main , @Param("ptid") List<Long> ptid , @Param("list") List<ProductCount> list , Pageable pageable);

    @Query("SELECT p FROM ProductCount p WHERE p IN :list and p.main=:main and p.price<=:amount")
    List<ProductCount> findProductCountByPrice(@Param("main") int main , @Param("amount") long amount  , @Param("list") List<ProductCount> list);


    @Query("SELECT p FROM ProductCount p  WHERE p IN :list and p.product.AmazingOffer=:amazingOffer and p.main=:main")
    List<ProductCount> findAmazingOffer(@Param("amazingOffer")  int amazingOffer ,  @Param("main") int main ,  @Param("list") List<ProductCount> list);

    @Query("SELECT p FROM ProductCount p  WHERE p IN :list and p.count>0 and p.main=:main ")
    List<ProductCount> findAvailableProduct(@Param("main") int main ,  @Param("list") List<ProductCount> list);


    @Query("SELECT DISTINCT p FROM ProductCount p  WHERE p IN :list and p.main=:main ")
    List<ProductCount> finalResultForAdvanceSearch(@Param("main") int main ,  @Param("list") List<ProductCount> list , Pageable pageable);


    /// these queries are for advance search ///








}

