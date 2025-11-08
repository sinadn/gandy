package com.example.gandy.service;

import com.example.gandy.entity.Category;
import com.example.gandy.entity.Users;
import com.example.gandy.exception.ObjectException;
import com.example.gandy.repo.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl{
    @Autowired
    UsersRepository usersRepository;



    public void createObjects(Users objects) {
        usersRepository.save(objects);
    }


    public Users findObjects(long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ObjectException("There is not any user by this id"));
    }


    public Users findUser(String mobile) {
        return usersRepository.findByIsMobile(mobile);
    }

    public List<Users> getUserByWords(String name) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  usersRepository.findUserByWords(name , paging);
    }


    public List<Users> findUserByMobile(String mbl) {
        Pageable paging = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "id"));
        return  usersRepository.findUserByMobile(mbl , paging);
    }


    public void deleteByIdObject(Long id) {
        usersRepository.deleteById(id);
    }


    public Users findUserById(long id) {
        return usersRepository.findUsersById(id);
    }



    public Page<Users> getAllUser(int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
        return usersRepository.findAll(paging);
    }



}
