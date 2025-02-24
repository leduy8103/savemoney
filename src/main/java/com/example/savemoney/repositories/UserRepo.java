package com.example.savemoney.repositories;

import com.example.savemoney.model.MyUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Integer> {
    MyUser findByEmail(String email);
    boolean existsByEmail(String email);
}
