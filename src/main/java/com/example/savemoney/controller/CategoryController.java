
package com.example.savemoney.controller;

import com.example.savemoney.model.MyUser;
import com.example.savemoney.model.TransactionType;
import com.example.savemoney.payload.request.CategoryRequest;
import com.example.savemoney.repositories.UserRepo;
import com.example.savemoney.services.CategoryService;
import com.example.savemoney.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepo userRepo;

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Category>> getAllCategoriesByUserId(@PathVariable int userId) {
        List<Category> categories = categoryService.getAllCategoriesByUserId(userId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/icon/{icon}")
    public ResponseEntity<List<Category>> getAllCategoriesByIcon(@PathVariable String icon) {
        List<Category> categories = categoryService.getAllCategoriesByIcon(icon);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<Category>> getAllCategoriesByUserIdAndType(
            @PathVariable int userId,
            @PathVariable String type) {
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        List<Category> categories = categoryService.getAllCategoriesByUserIdAndType(userId, transactionType);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            // Lấy email của user từ security context
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

            // Tìm user từ database
            MyUser user = userRepo.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

            // Tạo và lưu category
            Category savedCategory = categoryService.addCategory(categoryRequest, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating category: " + e.getMessage());
        }
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable int categoryId,
            @RequestBody Category updatedCategory) {
        Category category = categoryService.updateCategory(categoryId, updatedCategory);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}