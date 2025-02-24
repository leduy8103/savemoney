package com.example.savemoney.services;

import com.example.savemoney.model.Category;
import com.example.savemoney.model.MyUser;
import com.example.savemoney.model.TransactionType;
import com.example.savemoney.payload.request.CategoryRequest;
import com.example.savemoney.repositories.CategoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    /**
     * Retrieves a category by its name.
     *
     * @param categoryName the name of the category
     * @return the Category if found, otherwise null
     */
    public Category getCategoryByName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }

    /**
     * Retrieves all categories associated with a specific user ID.
     *
     * @param userId the user's ID
     * @return a list of categories
     */
    public List<Category> getAllCategoriesByUserId(int userId) {
        return categoryRepo.findAllByMyUser_UserId(userId);
    }

    /**
     * Retrieves all categories with a specific icon.
     *
     * @param icon the icon identifier
     * @return a list of categories
     */
    public List<Category> getAllCategoriesByIcon(String icon) {
        return categoryRepo.findAllByIcon(icon);
    }

    /**
     * Retrieves all categories associated with a specific user ID and transaction type.
     *
     * @param userId the user's ID
     * @param type   the transaction type
     * @return a list of categories
     */
    public List<Category> getAllCategoriesByUserIdAndType(int userId, TransactionType type) {
        return categoryRepo.findAllByMyUser_UserIdAndType(userId, type);
    }


    public Category addCategory(CategoryRequest categoryRequest, MyUser user) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setType(categoryRequest.getType());
        category.setIcon(categoryRequest.getIcon());
        category.setMyUser(user);

        return categoryRepo.save(category);
    }


    /**
     * Updates an existing category.
     *
     * @param categoryId the ID of the category to update
     * @param updatedCategory the Category object containing updated information
     * @return the updated Category object
     * @throws IllegalArgumentException if the category does not exist
     */
    @Transactional
    public Category updateCategory(int categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setIcon(updatedCategory.getIcon());
            existingCategory.setType(updatedCategory.getType());
            // Add any other fields that need to be updated
            return existingCategory;
        } else {
            throw new IllegalArgumentException("Category with ID " + categoryId + " not found.");
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId the ID of the category to delete
     * @throws IllegalArgumentException if the category does not exist
     */
    public void deleteCategory(int categoryId) {
        if (categoryRepo.existsById(categoryId)) {
            categoryRepo.deleteById(categoryId);
        } else {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
    }
}