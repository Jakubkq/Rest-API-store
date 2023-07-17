package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.CartService;
import com.kodilla.ecommercee.service.OrderService;
import com.kodilla.ecommercee.service.ProductService;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ecommerce/carts")
@RequiredArgsConstructor
public class CartController {

    private final ProductMapper productMapper;
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;



    @PostMapping(value = "/new/cart/{userId}")
    public ResponseEntity<Void> createEmptyCart(@PathVariable Long userId) {
        User userOwningCart = userService.getUser(userId);
        Cart newCart = new Cart(userOwningCart);
        cartService.saveCard(newCart);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{cartId}")
    public ResponseEntity<List<ProductDto>> getAllProductsFromCart(@PathVariable Long cartId) {
        Cart loadedCart = cartService.findCart(cartId);
        List<Product> loadedProductList = loadedCart.getProductList();
        List<ProductDto> loadedProductDtoList = productMapper.mapToProductDtoList(loadedProductList);
        return ResponseEntity.ok(loadedProductDtoList);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "{cartId}")
    public ResponseEntity<List<ProductDto>> addProductToCartByID(@PathVariable Long cartId, @RequestBody List<Long> productIdList) {
        Cart loadedCart = cartService.findCart(cartId);
        List<Product> loadedProductList = loadedCart.getProductList();
        List<Product> addedProductList = new ArrayList<>();

        for (Long id : productIdList) {
            addedProductList.add(productService.getProduct(id));
        }

        List<Product> mergeProductList = new ArrayList<>();
        mergeProductList.addAll(loadedProductList);
        mergeProductList.addAll(addedProductList);
        loadedCart.setProductList(mergeProductList);
        cartService.saveCard(loadedCart);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{cartId}/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        Cart loadedCart = cartService.findCart(cartId);
        List<Product> loadedProductList = loadedCart.getProductList();
        Product productToRemove = productService.getProduct(productId);
        loadedProductList.remove(productToRemove);
        loadedCart.setProductList(loadedProductList);
        cartService.saveCard(loadedCart);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/new/order/{cartId}")
    public ResponseEntity<String> createOrder(@PathVariable Long cartId) {
        String announcement = "";
        Cart loadedCart = cartService.findCart(cartId);
        User userOwningCart = loadedCart.getUser();

        if (userOwningCart.isActive() && userOwningCart.getKey() > 0 &&
                userOwningCart.getExpiryDate().isAfter(LocalDateTime.now().minus(Duration.ofHours(1)))) {
            Order newOrder = new Order(loadedCart);
            orderService.saveOrder(newOrder);
            announcement = "Order created";
        } else {
            announcement = "User not Active, order cancelled ";
        }
        return ResponseEntity.ok(announcement);
    }
}