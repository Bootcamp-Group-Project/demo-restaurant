package com.bootcamp.demo.demo_restaurant.model;

public class CartItemRequest {


    private Long menuId;
    private String chosenMenuItems; 
    private Integer quantity;

    // 顯式構造函數 (Explicit Constructor)
    public CartItemRequest() {}

    // Explicit Getters and Setters
    public Long getMenuId() { 
        return menuId; 
    }
    public void setMenuId(Long menuId) { 
        this.menuId = menuId; 
    }

    public String getChosenMenuItems() { 
        return chosenMenuItems; 
    }
    public void setChosenMenuItems(String chosenMenuItems) { 
        this.chosenMenuItems = chosenMenuItems; 
    }

    public Integer getQuantity() { 
        return quantity; 
    }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity; 
    }
}