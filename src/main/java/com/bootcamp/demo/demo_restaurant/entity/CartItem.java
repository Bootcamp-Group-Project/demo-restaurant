package com.bootcamp.demo.demo_restaurant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "chosen_menu_items", columnDefinition = "TEXT")
    private String chosenMenuItems; // JSON structure containing custom Menu_Item_IDs

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public CartItem() {}

    // Explicit Getters and Setters
    public Long getCartItemId() { return cartItemId; }
    public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Long getMenuId() { return menuId; }
    public void setMenuId(Long menuId) { this.menuId = menuId; }

    public String getChosenMenuItems() { return chosenMenuItems; }
    public void setChosenMenuItems(String chosenMenuItems) { this.chosenMenuItems = chosenMenuItems; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}