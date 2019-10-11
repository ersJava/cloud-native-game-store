package com.company.retailapiservice.viewmodel;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderForm {

    private Integer invoiceId;

    @NotNull(message = "Please supply a value for Customer ID")
    private Integer customerId;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a purchase date for order")
    private LocalDate purchaseDate;

    @NotEmpty(message = "Please supply an purchase item for invoice order")
    @Valid
    private List<InvoiceItem> itemList = new ArrayList<>();

    private int levelUpPointsEarned;

    private BigDecimal orderTotal;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getLevelUpPointsEarned() {
        return levelUpPointsEarned;
    }

    public void setLevelUpPointsEarned(int levelUpPointsEarned) {
        this.levelUpPointsEarned = levelUpPointsEarned;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<InvoiceItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<InvoiceItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderForm orderForm = (OrderForm) o;
        return getLevelUpPointsEarned() == orderForm.getLevelUpPointsEarned() &&
                getInvoiceId().equals(orderForm.getInvoiceId()) &&
                getCustomerId().equals(orderForm.getCustomerId()) &&
                getPurchaseDate().equals(orderForm.getPurchaseDate()) &&
                getItemList().equals(orderForm.getItemList()) &&
                getOrderTotal().equals(orderForm.getOrderTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate(), getItemList(), getLevelUpPointsEarned(), getOrderTotal());
    }
}
