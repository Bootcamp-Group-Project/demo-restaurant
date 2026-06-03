package com.bootcamp.demo.demo_restaurant.controller;

import com.bootcamp.demo.demo_restaurant.model.CartCheckoutPreviewDto;
import com.bootcamp.demo.demo_restaurant.model.CartItemRequest;
import com.bootcamp.demo.demo_restaurant.entity.Cart; 
import com.bootcamp.demo.demo_restaurant.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add or dynamically increment items inside the session cart.
     * 移除 @Valid，改用手動判斷，不依賴 jakarta 插件
     */
    @PostMapping("/items")
    public ResponseEntity<?> addItemToCart(
            @RequestHeader("X-User-Id") Long userId, 
            @RequestBody CartItemRequest request) {
        
        // 1. 手動進行防禦性參數檢查 (取代原本的 @NotNull 與 @Min)
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body cannot be null");
        }
        if (request.getMenuId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Menu ID cannot be null");
        }
        if (request.getQuantity() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity cannot be null");
        }
        if (request.getQuantity() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be at least 1");
        }
        
        // 2. 驗證通過，執行業務邏輯
        Cart updatedCart = cartService.addOrUpdateItem(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCart);
    }

    /**
     * Pre-checkout Engine Endpoint.
     */
    @GetMapping("/pre-checkout")
    public ResponseEntity<CartCheckoutPreviewDto> previewCartCheckout(@RequestHeader("X-User-Id") Long userId) {
        CartCheckoutPreviewDto preview = cartService.getPreCheckoutPreview(userId);
        return ResponseEntity.ok(preview);
    }

    /**
     * Wipe session cart details after successful conversions or expiration timelines.
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}