package com.company.invoiceservice.viewmodel;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItemViewModel {

    private Integer invoiceItemId;
    private Integer invoiceId;

    @NotNull(message = "Please supply an inventory ID")
    private Integer inventoryId;

    @NotNull(message = "Please supply a quantity for item")
    private Integer quantity;

    //    @NotNull(message = "Please supply a price for item")
    @Digits(integer = 7, fraction = 2)
    private BigDecimal unitPrice;

    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItemViewModel viewModel = (InvoiceItemViewModel) o;
        return getInvoiceItemId().equals(viewModel.getInvoiceItemId()) &&
                getInvoiceId().equals(viewModel.getInvoiceId()) &&
                Objects.equals(getInventoryId(), viewModel.getInventoryId()) &&
                Objects.equals(getQuantity(), viewModel.getQuantity()) &&
                Objects.equals(getUnitPrice(), viewModel.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceItemId(), getInvoiceId(), getInventoryId(), getQuantity(), getUnitPrice());
    }
}
