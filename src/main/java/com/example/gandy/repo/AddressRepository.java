package com.example.gandy.repo;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.SubCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {


    Address findAddressByIdAndUsersMobile(long id, String mobile);

    Address findAddressById(long id);


    List<Address> findAddressByUsersId(long id);


    List<Address> findAddressesByUsersMobile(String mobile);

    void deleteAddressByIdAndUsersMobile(long id , String mobile);


}