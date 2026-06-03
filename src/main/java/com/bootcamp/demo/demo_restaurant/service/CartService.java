package com.bootcamp.demo.demo_restaurant.service;

import com.bootcamp.demo.demo_restaurant.model.CartCheckoutPreviewDto;
import com.bootcamp.demo.demo_restaurant.model.CartItemRequest;
import com.bootcamp.demo.demo_restaurant.model.CartItemSummaryDto;
import com.bootcamp.demo.demo_restaurant.entity.Cart;
import com.bootcamp.demo.demo_restaurant.entity.CartItem;
import com.bootcamp.demo.demo_restaurant.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

   
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Cart addOrUpdateItem(Long userId, CartItemRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getMenuId().equals(request.getMenuId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setChosenMenuItems(request.getChosenMenuItems());
        } else {
            CartItem newItem = new CartItem();
            newItem.setMenuId(request.getMenuId());
            newItem.setQuantity(request.getQuantity());
            newItem.setChosenMenuItems(request.getChosenMenuItems());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public CartCheckoutPreviewDto getPreCheckoutPreview(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));

        CartCheckoutPreviewDto preview = new CartCheckoutPreviewDto();
        List<CartItemSummaryDto> itemSummaries = new ArrayList<>();
        
        int totalQuantity = 0;
        BigDecimal baseTotal = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            BigDecimal basePrice = new BigDecimal("40.00"); 
            BigDecimal customMarkupTotal = BigDecimal.ZERO;

            if (item.getChosenMenuItems() != null && !item.getChosenMenuItems().trim().isEmpty()) {
                customMarkupTotal = new BigDecimal("5.00");
            }

            BigDecimal actualUnitPrice = basePrice.add(customMarkupTotal);
            BigDecimal subTotal = actualUnitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            CartItemSummaryDto itemSummary = new CartItemSummaryDto();
            itemSummary.setCartItemId(item.getCartItemId());
            itemSummary.setMenuId(item.getMenuId());
            itemSummary.setQuantity(item.getQuantity());
            itemSummary.setUnitPrice(actualUnitPrice); 
            itemSummary.setSubTotal(subTotal);
            
            itemSummaries.add(itemSummary);

            totalQuantity += item.getQuantity();
            baseTotal = baseTotal.add(subTotal);
        }

        BigDecimal depositAmt = (totalQuantity >= 4) ? new BigDecimal("50.00") : BigDecimal.ZERO;

        preview.setTotalQuantity(totalQuantity);
        preview.setBaseTotal(baseTotal);
        preview.setDepositAmount(depositAmt);
        preview.setFinalEstimatedTotal(baseTotal.add(depositAmt));
        preview.setItems(itemSummaries);

        return preview;
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}