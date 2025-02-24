package com.example.savemoney.services;

import com.example.savemoney.model.Transaction;
import com.example.savemoney.model.TransactionType;
import com.example.savemoney.repositories.TransactionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;

    /**
     * Retrieves all transactions for a specific user.
     *
     * @param userId the user's ID
     * @return a list of Transaction
     */
    public List<Transaction> getAllTransactionsByUserId(int userId) {
        return transactionRepo.findAllByMyUser_UserId(userId);
    }

    /**
     * Retrieves all transactions for a specific category.
     *
     * @param categoryId the category's ID
     * @return a list of Transaction
     */
    public List<Transaction> getAllTransactionsByCategoryId(int categoryId) {
        return transactionRepo.findAllByCategory_CategoryId(categoryId);
    }

    /**
     * Retrieves all transactions of a specific type.
     *
     * @param type the TransactionType
     * @return a list of Transaction
     */
    public List<Transaction> getAllTransactionsByType(TransactionType type) {
        return transactionRepo.findAllByType(type);
    }

    /**
     * Retrieves all transactions within a specific date range.
     *
     * @param startDate the start date and time
     * @param endDate   the end date and time
     * @return a list of Transaction
     */
    public List<Transaction> getAllTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepo.findAllByTransactionDateBetween(startDate, endDate);
    }

    /**
     * Retrieves all transactions for a user within a specific date range.
     *
     * @param userId    the user's ID
     * @param startDate the start date and time
     * @param endDate   the end date and time
     * @return a list of Transaction
     */
    public List<Transaction> getAllTransactionsByUserIdAndDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepo.findAllByMyUser_UserIdAndTransactionDateBetween(userId, startDate, endDate);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionId the ID of the transaction
     * @return the Transaction if found
     * @throws IllegalArgumentException if the transaction does not exist
     */
    public Transaction getTransactionById(int transactionId) {
        return transactionRepo.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + transactionId + " not found."));
    }

    /**
     * Adds a new transaction.
     *
     * @param transaction the Transaction to add
     * @return the saved Transaction
     */
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    /**
     * Updates an existing transaction.
     *
     * @param transactionId   the ID of the transaction to update
     * @param updatedTransaction the Transaction object containing updated information
     * @return the updated Transaction
     * @throws IllegalArgumentException if the transaction does not exist
     */
    @Transactional
    public Transaction updateTransaction(int transactionId, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction with ID " + transactionId + " not found."));

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setTransactionDate(updatedTransaction.getTransactionDate());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        // Update other fields as necessary

        return existingTransaction;
    }

    /**
     * Deletes a transaction by its ID.
     *
     * @param transactionId the ID of the transaction to delete
     * @throws IllegalArgumentException if the transaction does not exist
     */
    public void deleteTransaction(int transactionId) {
        if (transactionRepo.existsById(transactionId)) {
            transactionRepo.deleteById(transactionId);
        } else {
            throw new IllegalArgumentException("Transaction with ID " + transactionId + " does not exist.");
        }
    }
}