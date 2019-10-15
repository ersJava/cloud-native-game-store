package com.company.adminapiservice.viewmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class InventoryViewModel {

    private int inventoryId;

    @NotNull(message = "Please supply a productId")
    @Min(value = 1, message = "productId must be a positive integer number")
    private Integer productId;

    @NotNull(message = "Please supply an Integer number for quantity")
    @Min(value = 0, message = "quantity for inventory must be a positive number or 0")
    private Integer quantity;

    //getters and setters
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryViewModel that = (InventoryViewModel) o;
        return getInventoryId() == that.getInventoryId() &&
                Objects.equals(getProductId(), that.getProductId()) &&
                Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }
}
