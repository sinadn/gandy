package com.example.gandy.repo;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.mobile=?1")
    Users findByIsMobile(String mbl);


    @Query("SELECT u FROM Users u   WHERE u.name like %:name%  ")
    List<Users> findUserByWords(@Param("name") String name , Pageable pageable);

    @Query("SELECT u FROM Users u   WHERE u.mobile like %:mbl%  ")
    List<Users> findUserByMobile(@Param("mbl") String mbl , Pageable pageable);


    Users findUsersById(long id);

//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);
}