package com.example.savemoney.repositories;

import com.example.savemoney.model.Transaction;
import com.example.savemoney.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByMyUser_UserId(int userId);
    List<Transaction> findAllByCategory_CategoryId(int categoryId);
    List<Transaction> findAllByType(TransactionType type);
    List<Transaction> findAllByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findAllByMyUser_UserIdAndTransactionDateBetween(
            int userId, LocalDateTime startDate, LocalDateTime endDate);
}
