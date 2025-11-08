package com.example.gandy.service;

import com.example.gandy.entity.*;
import com.example.gandy.payload.request.AddressInfoRequest;
import com.example.gandy.repo.AddressRepository;
import com.example.gandy.repo.UsersRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AddressServiceImpl {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UsersServiceImpl usersService;

    public void addObject(AddressInfoRequest object) {
        Users users = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        try {
            users = usersService.findUser(userDetails1.getMobile());
        } catch (Exception e) {
            e.getMessage();
        }


        Address address = new Address();
        address.setAddress(object.getAddress());

        if (object.getId()!=0) {
            address.setId(object.getId());
        }
        address.setUsers(users);
        City city = new City();
        city.setId(object.getCity());
        address.setCity(city);
        Province province = new Province();
        province.setId(object.getProvince());
        address.setProvince(province);
        address.setPostalCode(object.getPostalCode());
        address.setNo(object.getNo());
        address.setArea(object.getArea());
        address.setUnit(object.getUnit());
        addressRepository.save(address);
    }




    public void addObjectByAdmin(Address object) {
        addressRepository.save(object);
    }


    public Collection<Address> getObjects() {
        return addressRepository.findAll();
    }

    public void deleteAddress(long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails ;
        try {
            addressRepository.deleteAddressByIdAndUsersMobile(id,userDetails1.getMobile());
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void deleteAdd(long id) {
            addressRepository.deleteById(id);
    }


    public Address getObject(long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        return addressRepository.findAddressByIdAndUsersMobile(id,userDetails1.getMobile());
    }

    public Address findAddress(long id) {
        return addressRepository.findAddressById(id);
    }

    public List<Address> findObjects() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        return addressRepository.findAddressesByUsersMobile(userDetails1.getMobile());
    }

    public Page<Address> findAddressList(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return addressRepository.findAll(paging);
    }

    public List<Address> getAddressListByMobile(String mobile) {
        return addressRepository.findAddressesByUsersMobile(mobile);
    }


}
