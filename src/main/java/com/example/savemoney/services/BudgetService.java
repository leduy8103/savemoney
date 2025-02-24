package com.example.savemoney.services;

import com.example.savemoney.model.Budget;
import com.example.savemoney.repositories.BudgetRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepo budgetRepo;

    /**
     * Retrieves all budgets for a specific user.
     *
     * @param userId the user's ID
     * @return a list of Budget
     */
    public List<Budget> getAllBudgetsByUserId(int userId) {
        return budgetRepo.findAllByMyUser_UserId(userId);
    }

    /**
     * Retrieves all budgets for a specific category.
     *
     * @param categoryId the category's ID
     * @return a list of Budget
     */
    public List<Budget> getAllBudgetsByCategoryId(int categoryId) {
        return budgetRepo.findAllByCategory_CategoryId(categoryId);
    }

    /**
     * Retrieves all active budgets for a user on a specific date.
     *
     * @param userId the user's ID
     * @param date   the date to check
     * @return a list of Budget
     */
    public List<Budget> getActiveBudgetsByUserIdAndDate(int userId, LocalDate date) {
        return budgetRepo.findByMyUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                userId, date, date);
    }

    /**
     * Retrieves a budget by its ID.
     *
     * @param budgetId the ID of the budget
     * @return the Budget if found
     * @throws IllegalArgumentException if the budget does not exist
     */
    public Budget getBudgetById(int budgetId) {
        return budgetRepo.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget with ID " + budgetId + " not found."));
    }

    /**
     * Adds a new budget.
     *
     * @param budget the Budget to add
     * @return the saved Budget
     */
    public Budget addBudget(Budget budget) {
        return budgetRepo.save(budget);
    }

    /**
     * Updates an existing budget.
     *
     * @param budgetId     the ID of the budget to update
     * @param updatedBudget the Budget object containing updated information
     * @return the updated Budget
     * @throws IllegalArgumentException if the budget does not exist
     */
    @Transactional
    public Budget updateBudget(int budgetId, Budget updatedBudget) {
        Budget existingBudget = budgetRepo.findById(budgetId)
                .orElseThrow(() -> new IllegalArgumentException("Budget with ID " + budgetId + " not found."));

        existingBudget.setLimitAmount(updatedBudget.getLimitAmount());
        existingBudget.setStartDate(updatedBudget.getStartDate());
        existingBudget.setEndDate(updatedBudget.getEndDate());
        existingBudget.setCategory(updatedBudget.getCategory());
        // Update other fields as necessary

        return existingBudget;
    }

    /**
     * Deletes a budget by its ID.
     *
     * @param budgetId the ID of the budget to delete
     * @throws IllegalArgumentException if the budget does not exist
     */
    public void deleteBudget(int budgetId) {
        if (budgetRepo.existsById(budgetId)) {
            budgetRepo.deleteById(budgetId);
        } else {
            throw new IllegalArgumentException("Budget with ID " + budgetId + " does not exist.");
        }
    }
}