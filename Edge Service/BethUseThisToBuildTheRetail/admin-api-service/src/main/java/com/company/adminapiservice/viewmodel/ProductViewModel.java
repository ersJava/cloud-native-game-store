package com.company.adminapiservice.viewmodel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ProductViewModel {

    private int productId;

    @NotBlank(message = "productName is required!")
    @Size(max = 50, message = "productName can not be longer than 50 characters")
    private String productName;

    @NotBlank(message = "firstName is required!")
    @Size(max = 255, message = "productDescription can not be longer than 255 characters")
    private String productDescription;

    @NotBlank(message = "listPrice is required!")
    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "listPrice must be a positive number with less than two decimal positions")
    private String listPrice;

    @NotBlank(message = "unitCost is required!")
    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "unitCost must be a positive number with less than two decimal positions")
    private String unitCost;

    //getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductViewModel that = (ProductViewModel) o;
        return getProductId() == that.getProductId() &&
                getProductName().equals(that.getProductName()) &&
                getProductDescription().equals(that.getProductDescription()) &&
                getListPrice().equals(that.getListPrice()) &&
                getUnitCost().equals(that.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getUnitCost());
    }
}
