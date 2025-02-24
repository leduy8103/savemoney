package com.example.savemoney.repositories;

import com.example.savemoney.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByMyUser_UserId(int userId);
    List<Budget> findAllByCategory_CategoryId(int categoryId);
    List<Budget> findByMyUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            int userId, LocalDate date, LocalDate sameDate);
}