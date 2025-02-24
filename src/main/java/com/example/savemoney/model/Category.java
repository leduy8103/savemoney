package com.example.savemoney.model;

import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String icon;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;
}

