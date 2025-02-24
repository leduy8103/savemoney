
package com.example.savemoney.controller;

import com.example.savemoney.services.TransactionService;
import com.example.savemoney.model.Transaction;
import com.example.savemoney.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUserId(@PathVariable int userId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByCategoryId(@PathVariable int categoryId) {
        List<Transaction> transactions = transactionService.getAllTransactionsByCategoryId(categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByType(@PathVariable String type) {
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        List<Transaction> transactions = transactionService.getAllTransactionsByType(transactionType);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<Transaction>> getAllTransactionsBetweenDates(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Transaction> transactions = transactionService.getAllTransactionsBetweenDates(start, end);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/dates")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUserIdAndDateRange(
            @PathVariable int userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<Transaction> transactions = transactionService.getAllTransactionsByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.addTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable int transactionId,
            @RequestBody Transaction updatedTransaction) {
        Transaction transaction = transactionService.updateTransaction(transactionId, updatedTransaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}