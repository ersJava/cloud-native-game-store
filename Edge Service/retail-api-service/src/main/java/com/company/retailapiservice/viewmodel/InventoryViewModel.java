package com.company.retailapiservice.viewmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class InventoryViewModel {

    private Integer inventoryId;

    @NotNull(message = "Please supply a value for product ID")
    private Integer productId;

    @Min(value = 0, message = "Product quantity must be 0 or greater")
    @NotNull(message = "Please supply a value for quantity")
    private int quantity;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryViewModel that = (InventoryViewModel) o;
        return getQuantity() == that.getQuantity() &&
                getInventoryId().equals(that.getInventoryId()) &&
                getProductId().equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }
}
