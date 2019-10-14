package com.company.adminapiservice.viewmodel;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {

    @NotNull(message = "Please supply a value for customerId")
    private Integer customerId;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a purchase date for order")
    private LocalDate purchaseDate;

    @Valid
    private List<ProductToBuyViewModel> productsToBuy;

    private double orderTotal;
    private int pointsEarned;

    private int totalPoints;

    private InvoiceViewModel invoice;

    //getters and setters
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

    public List<ProductToBuyViewModel> getProductsToBuy() {
        return productsToBuy;
    }

    public void setProductsToBuy(List<ProductToBuyViewModel> productsToBuy) {
        this.productsToBuy = productsToBuy;
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

    public InvoiceViewModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceViewModel invoice) {
        this.invoice = invoice;
    }


    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return Double.compare(that.getOrderTotal(), getOrderTotal()) == 0 &&
                getPointsEarned() == that.getPointsEarned() &&
                getTotalPoints() == that.getTotalPoints() &&
                Objects.equals(getCustomerId(), that.getCustomerId()) &&
                Objects.equals(getPurchaseDate(), that.getPurchaseDate()) &&
                Objects.equals(getProductsToBuy(), that.getProductsToBuy()) &&
                Objects.equals(getInvoice(), that.getInvoice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getPurchaseDate(), getProductsToBuy(), getOrderTotal(), getPointsEarned(), getTotalPoints(), getInvoice());
    }
}
