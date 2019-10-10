package com.company.invoiceservice.model;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice {

    private int invoiceId;

    @NotNull(message = "Please supply a value for Customer ID")
    private int customerId;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a purchase date for order")
    private LocalDate purchaseDate;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getInvoiceId() == invoice.getInvoiceId() &&
                getCustomerId() == invoice.getCustomerId() &&
                getPurchaseDate().equals(invoice.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate());
    }
}
