package com.company.retailapiservice.viewmodel;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private Integer invoiceId;

    @NotNull(message = "Please supply a value for Customer ID")
    private Integer customerId;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a purchase date for order")
    private LocalDate purchaseDate;

    @NotEmpty(message = "Please supply an purchase item for invoice order")
    @Valid
    private List<InvoiceItemViewModel> itemList = new ArrayList<>();

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

    public List<InvoiceItemViewModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<InvoiceItemViewModel> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return Objects.equals(getInvoiceId(), that.getInvoiceId()) &&
                Objects.equals(getCustomerId(), that.getCustomerId()) &&
                Objects.equals(getPurchaseDate(), that.getPurchaseDate()) &&
                Objects.equals(getItemList(), that.getItemList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate(), getItemList());
    }
}
