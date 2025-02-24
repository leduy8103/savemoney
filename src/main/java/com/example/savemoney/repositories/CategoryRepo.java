package com.example.savemoney.repositories;

import com.example.savemoney.model.Category;
import com.example.savemoney.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
    List<Category> findAllByMyUser_UserId(int userId);
    List<Category> findAllByIcon(String icon);
    List<Category> findAllByMyUser_UserIdAndType(int userId, TransactionType type);
}
