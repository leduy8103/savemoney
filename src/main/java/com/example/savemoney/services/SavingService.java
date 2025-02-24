package com.example.savemoney.services;

import com.example.savemoney.model.SavingsGoal;
import com.example.savemoney.repositories.SavingRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavingService {

    private final SavingRepo savingRepo;

    /**
     * Retrieves all savings goals for a specific user.
     *
     * @param userId the user's ID
     * @return a list of SavingsGoal
     */
    public List<SavingsGoal> getAllSavingsByUserId(int userId) {
        return savingRepo.findAllByMyUser_UserId(userId);
    }

    /**
     * Calculates the total saved amount for a user within a specified date range.
     *
     * @param userId    the user's ID
     * @param startDate the start date
     * @param endDate   the end date
     * @return the total saved amount as BigDecimal
     */
    public BigDecimal getTotalSaving(int userId, LocalDate startDate, LocalDate endDate) {
        return savingRepo.getTotalSaving(userId, startDate, endDate);
    }

    /**
     * Retrieves a savings goal by its ID.
     *
     * @param savingId the ID of the savings goal
     * @return the SavingsGoal if found
     * @throws IllegalArgumentException if the savings goal does not exist
     */
    public SavingsGoal getSavingById(int savingId) {
        return savingRepo.findById(savingId)
                .orElseThrow(() -> new IllegalArgumentException("SavingsGoal with ID " + savingId + " not found."));
    }

    /**
     * Adds a new savings goal.
     *
     * @param savingsGoal the SavingsGoal to add
     * @return the saved SavingsGoal
     */
    public SavingsGoal addSaving(SavingsGoal savingsGoal) {
        return savingRepo.save(savingsGoal);
    }

    /**
     * Updates an existing savings goal.
     *
     * @param savingId      the ID of the savings goal to update
     * @param updatedSaving the SavingsGoal object containing updated information
     * @return the updated SavingsGoal
     * @throws IllegalArgumentException if the savings goal does not exist
     */
    @Transactional
    public SavingsGoal updateSaving(int savingId, SavingsGoal updatedSaving) {
        SavingsGoal existingSaving = savingRepo.findById(savingId)
                .orElseThrow(() -> new IllegalArgumentException("SavingsGoal with ID " + savingId + " not found."));

        existingSaving.setTargetAmount(updatedSaving.getTargetAmount());
        existingSaving.setSavedAmount(updatedSaving.getSavedAmount());
        existingSaving.setDeadline(updatedSaving.getDeadline());
        // Update other fields as necessary

        return existingSaving;
    }

    /**
     * Deletes a savings goal by its ID.
     *
     * @param savingId the ID of the savings goal to delete
     * @throws IllegalArgumentException if the savings goal does not exist
     */
    public void deleteSaving(int savingId) {
        if (savingRepo.existsById(savingId)) {
            savingRepo.deleteById(savingId);
        } else {
            throw new IllegalArgumentException("SavingsGoal with ID " + savingId + " does not exist.");
        }
    }
}