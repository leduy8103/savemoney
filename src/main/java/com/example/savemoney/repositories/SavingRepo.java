package com.example.savemoney.repositories;

import com.example.savemoney.model.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingRepo extends JpaRepository<SavingsGoal, Integer> {
    List<SavingsGoal> findAllByMyUser_UserId(int userId);

    @Query("SELECT SUM(s.savedAmount) FROM SavingsGoal s WHERE s.myUser.userId = :userId AND s.deadline BETWEEN :startDate AND :endDate")
    BigDecimal getTotalSaving(@Param("userId") int userId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
}
