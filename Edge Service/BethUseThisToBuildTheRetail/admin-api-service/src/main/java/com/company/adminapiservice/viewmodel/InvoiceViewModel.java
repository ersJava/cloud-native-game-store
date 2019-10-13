package com.company.adminapiservice.viewmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private Integer invoiceId;

    private Integer customerId;
    private LocalDate purchaseDate;

    private List<InvoiceItemViewModel> itemList;

    //getters and setters
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

    //equals and hashcode
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
