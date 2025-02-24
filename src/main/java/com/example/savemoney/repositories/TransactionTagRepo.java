package com.example.savemoney.repositories;

import com.example.savemoney.model.TransactionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionTagRepo extends JpaRepository<TransactionTag, Integer> {
    List<TransactionTag> findAllByTransaction_Id(int transactionId);
    List<TransactionTag> findAllByTag(String tag);
    List<TransactionTag> findAllByTransaction_MyUser_UserId(int userId);
}
