package com.example.gandy.repo;

import com.example.gandy.entity.BankCard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    BankCard findBankCardByUsersIdAndId(long userId,long id);
    List<BankCard> findBankCardByUsersId(long id);

       void deleteBankCardByUsersIdAndId(long userId , long id);

}