package com.kodilla.ecommercee.domain.dto;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartDto {
    private Long id;
    private User user;
    private List<Product> productList;
}
