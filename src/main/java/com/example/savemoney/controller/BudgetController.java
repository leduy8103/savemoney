package com.example.savemoney.controller;

import com.example.savemoney.services.BudgetService;
import com.example.savemoney.model.Budget;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getAllBudgetsByUserId(@PathVariable int userId) {
        List<Budget> budgets = budgetService.getAllBudgetsByUserId(userId);
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Budget>> getAllBudgetsByCategoryId(@PathVariable int categoryId) {
        List<Budget> budgets = budgetService.getAllBudgetsByCategoryId(categoryId);
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Budget>> getActiveBudgets(
            @RequestParam int userId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Budget> budgets = budgetService.getActiveBudgetsByUserIdAndDate(userId, localDate);
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable int budgetId) {
        Budget budget = budgetService.getBudgetById(budgetId);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Budget> addBudget(@RequestBody Budget budget) {
        Budget savedBudget = budgetService.addBudget(budget);
        return new ResponseEntity<>(savedBudget, HttpStatus.CREATED);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable int budgetId,
            @RequestBody Budget updatedBudget) {
        Budget budget = budgetService.updateBudget(budgetId, updatedBudget);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable int budgetId) {
        budgetService.deleteBudget(budgetId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}