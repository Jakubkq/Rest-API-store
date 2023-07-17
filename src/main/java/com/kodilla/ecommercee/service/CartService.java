package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    public final CartRepository cartRepository;

    public List<Cart> findCarts() {
        return cartRepository.findAll();
    }

    public Cart findCart(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCard (Cart cart){
        return cartRepository.save(cart);
    }

}
