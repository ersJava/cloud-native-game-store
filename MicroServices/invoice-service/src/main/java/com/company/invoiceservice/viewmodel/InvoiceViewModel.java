package com.company.invoiceservice.viewmodel;

import com.company.invoiceservice.model.InvoiceItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private Integer invoiceId;
    private Integer customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> itemList = new ArrayList<>();

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
        InvoiceViewModel that = (InvoiceViewModel) o;
        return getInvoiceId().equals(that.getInvoiceId()) &&
                getCustomerId().equals(that.getCustomerId()) &&
                getPurchaseDate().equals(that.getPurchaseDate()) &&
                getItemList().equals(that.getItemList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate(), getItemList());
    }
}
