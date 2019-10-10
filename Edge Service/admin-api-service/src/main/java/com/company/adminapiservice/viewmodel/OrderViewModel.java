package com.company.adminapiservice.viewmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {

    private int customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItems;

    private double orderTotal;
    private int pointsEarned;

    private int totalPoints;

    //getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                Double.compare(that.getOrderTotal(), getOrderTotal()) == 0 &&
                getPointsEarned() == that.getPointsEarned() &&
                getTotalPoints() == that.getTotalPoints() &&
                getPurchaseDate().equals(that.getPurchaseDate()) &&
                getInvoiceItems().equals(that.getInvoiceItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getPurchaseDate(), getInvoiceItems(), getOrderTotal(), getPointsEarned(), getTotalPoints());
    }
}
