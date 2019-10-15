package com.company.adminapiservice.viewmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ProductToBuyViewModel {

    @NotNull(message = "Please supply a productId")
    @Min(value = 1, message = "productId must be a positive integer number greater than 0")
    private Integer productId;

    @NotNull(message = "Please supply an Integer number for quantity")
    @Min(value = 1, message = "quantity to buy must be at least one")
    private Integer quantity;

    //getters and setters

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
        ProductToBuyViewModel product = (ProductToBuyViewModel) o;
        return Objects.equals(getProductId(), product.getProductId()) &&
                Objects.equals(getQuantity(), product.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getQuantity());
    }
}
