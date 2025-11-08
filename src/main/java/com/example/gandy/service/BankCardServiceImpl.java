package com.example.gandy.service;

import com.example.gandy.entity.BankCard;
import com.example.gandy.payload.request.BankCardRequest;
import com.example.gandy.repo.BankCardRepository;
import com.example.gandy.repo.UsersRepository;
import com.example.gandy.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankCardServiceImpl {
    @Autowired
    BankCardRepository repository;

    @Autowired
    UsersRepository usersRepository;


    public void addObject(BankCardRequest object) {
        BankCard bankCard = new BankCard();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        bankCard.setCardNum(object.getCardNum());
        bankCard.setName(object.getName());
        bankCard.setShaba(object.getShaba());
        bankCard.setFamily(object.getFamily());
        bankCard.setUsers(usersRepository.findUsersById(userDetails1.getId()));
        repository.save(bankCard);
    }



    public void update(BankCard bankCard) {
        repository.save(bankCard);
    }

    public BankCard getByIdObject(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        return repository.findBankCardByUsersIdAndId(userDetails1.getId(),id);
    }

    public List<BankCard> getAllObjects() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        return repository.findBankCardByUsersId(userDetails1.getId());
    }

    public List<BankCard> getAllByUserId(long id) {
        return repository.findBankCardByUsersId(id);
    }

    public void deleteByIdObject(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        repository.deleteBankCardByUsersIdAndId(userDetails1.getId(), id);
    }

}
