
package com.example.savemoney.controller;

import com.example.savemoney.services.SavingService;
import com.example.savemoney.model.SavingsGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/savings")
@RequiredArgsConstructor
public class SavingController {

    private final SavingService savingService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavingsGoal>> getAllSavingsByUserId(@PathVariable int userId) {
        List<SavingsGoal> savings = savingService.getAllSavingsByUserId(userId);
        return new ResponseEntity<>(savings, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalSaving(
            @RequestParam int userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        BigDecimal totalSaving = savingService.getTotalSaving(userId, start, end);
        return new ResponseEntity<>(totalSaving, HttpStatus.OK);
    }

    @GetMapping("/{savingId}")
    public ResponseEntity<SavingsGoal> getSavingById(@PathVariable int savingId) {
        SavingsGoal saving = savingService.getSavingById(savingId);
        return new ResponseEntity<>(saving, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SavingsGoal> addSaving(@RequestBody SavingsGoal savingsGoal) {
        SavingsGoal savedSaving = savingService.addSaving(savingsGoal);
        return new ResponseEntity<>(savedSaving, HttpStatus.CREATED);
    }

    @PutMapping("/{savingId}")
    public ResponseEntity<SavingsGoal> updateSaving(
            @PathVariable int savingId,
            @RequestBody SavingsGoal updatedSaving) {
        SavingsGoal saving = savingService.updateSaving(savingId, updatedSaving);
        return new ResponseEntity<>(saving, HttpStatus.OK);
    }

    @DeleteMapping("/{savingId}")
    public ResponseEntity<Void> deleteSaving(@PathVariable int savingId) {
        savingService.deleteSaving(savingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}