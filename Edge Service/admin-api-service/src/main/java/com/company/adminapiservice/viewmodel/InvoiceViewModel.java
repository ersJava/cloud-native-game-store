package com.company.adminapiservice.viewmodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    //The validation is do through the OrderViewModel
    private Integer invoiceId;

    private Integer customerId;
    private LocalDate purchaseDate;

<<<<<<< HEAD
<<<<<<< HEAD
    private List<InvoiceItemViewModel> invoiceItems;
=======
    private List<InvoiceItemViewModel> itemList;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
=======
    private List<InvoiceItemViewModel> itemList;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

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

<<<<<<< HEAD
<<<<<<< HEAD
    public List<InvoiceItemViewModel> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemViewModel> invoiceItems) {
        this.invoiceItems = invoiceItems;
=======
    public List<InvoiceItemViewModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<InvoiceItemViewModel> itemList) {
        this.itemList = itemList;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
=======
    public List<InvoiceItemViewModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<InvoiceItemViewModel> itemList) {
        this.itemList = itemList;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
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
