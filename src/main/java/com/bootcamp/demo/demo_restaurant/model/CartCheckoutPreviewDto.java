package com.bootcamp.demo.demo_restaurant.model;

import java.math.BigDecimal;
import java.util.List;

public class CartCheckoutPreviewDto {
    private int totalQuantity;
    private BigDecimal baseTotal;
    private BigDecimal depositAmount;
    private BigDecimal finalEstimatedTotal;
    private List<CartItemSummaryDto> items;

    // Getters and Setters
    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public BigDecimal getBaseTotal() { return baseTotal; }
    public void setBaseTotal(BigDecimal baseTotal) { this.baseTotal = baseTotal; }

    public BigDecimal getDepositAmount() { return depositAmount; }
    public void setDepositAmount(BigDecimal depositAmount) { this.depositAmount = depositAmount; }

    public BigDecimal getFinalEstimatedTotal() { return finalEstimatedTotal; }
    public void setFinalEstimatedTotal(BigDecimal finalEstimatedTotal) { this.finalEstimatedTotal = finalEstimatedTotal; }

    public List<CartItemSummaryDto> getItems() { return items; }
    public void setItems(List<CartItemSummaryDto> items) { this.items = items; }
}
