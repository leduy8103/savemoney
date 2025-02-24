
package com.example.savemoney.controller;

import com.example.savemoney.services.TransactionTagService;
import com.example.savemoney.model.TransactionTag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-tags")
@RequiredArgsConstructor
public class TransactionTagController {

    private final TransactionTagService transactionTagService;

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<TransactionTag>> getAllTagsByTransactionId(@PathVariable int transactionId) {
        List<TransactionTag> tags = transactionTagService.getAllTagsByTransactionId(transactionId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<TransactionTag>> getAllTransactionTagsByTag(@PathVariable String tag) {
        List<TransactionTag> tags = transactionTagService.getAllTransactionTagsByTag(tag);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionTag>> getAllTransactionTagsByUserId(@PathVariable int userId) {
        List<TransactionTag> tags = transactionTagService.getAllTransactionTagsByUserId(userId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionTag> addTransactionTag(@RequestBody TransactionTag transactionTag) {
        TransactionTag savedTag = transactionTagService.addTransactionTag(transactionTag);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionTagId}")
    public ResponseEntity<Void> deleteTransactionTag(@PathVariable int transactionTagId) {
        transactionTagService.deleteTransactionTag(transactionTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<Void> deleteTransactionTagsByTransactionId(@PathVariable int transactionId) {
        transactionTagService.deleteTransactionTagsByTransactionId(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}