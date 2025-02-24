package com.example.savemoney.services;

import com.example.savemoney.model.TransactionTag;
import com.example.savemoney.repositories.TransactionTagRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTagService {

    private final TransactionTagRepo transactionTagRepo;

    /**
     * Retrieves all tags associated with a specific transaction.
     *
     * @param transactionId the transaction's ID
     * @return a list of TransactionTag
     */
    public List<TransactionTag> getAllTagsByTransactionId(int transactionId) {
        return transactionTagRepo.findAllByTransaction_Id(transactionId);
    }

    /**
     * Retrieves all TransactionTag entries with a specific tag.
     *
     * @param tag the tag to search for
     * @return a list of TransactionTag
     */
    public List<TransactionTag> getAllTransactionTagsByTag(String tag) {
        return transactionTagRepo.findAllByTag(tag);
    }

    /**
     * Retrieves all TransactionTag entries associated with a specific user.
     *
     * @param userId the user's ID
     * @return a list of TransactionTag
     */
    public List<TransactionTag> getAllTransactionTagsByUserId(int userId) {
        return transactionTagRepo.findAllByTransaction_MyUser_UserId(userId);
    }

    /**
     * Adds a new TransactionTag.
     *
     * @param transactionTag the TransactionTag to add
     * @return the saved TransactionTag
     */
    public TransactionTag addTransactionTag(TransactionTag transactionTag) {
        return transactionTagRepo.save(transactionTag);
    }

    /**
     * Deletes a TransactionTag by its ID.
     *
     * @param transactionTagId the ID of the TransactionTag to delete
     * @throws IllegalArgumentException if the TransactionTag does not exist
     */
    public void deleteTransactionTag(int transactionTagId) {
        if (transactionTagRepo.existsById(transactionTagId)) {
            transactionTagRepo.deleteById(transactionTagId);
        } else {
            throw new IllegalArgumentException("TransactionTag with ID " + transactionTagId + " does not exist.");
        }
    }

    /**
     * Deletes all TransactionTag entries associated with a specific transaction.
     *
     * @param transactionId the ID of the transaction
     */
    @Transactional
    public void deleteTransactionTagsByTransactionId(int transactionId) {
        List<TransactionTag> tags = transactionTagRepo.findAllByTransaction_Id(transactionId);
        transactionTagRepo.deleteAll(tags);
    }
}