package com.example.savemoney.payload.request;

import com.example.savemoney.model.TransactionType;
import lombok.Data;

@Data
public class CategoryRequest {
    private String categoryName;
    private TransactionType type;
    private String icon;
}