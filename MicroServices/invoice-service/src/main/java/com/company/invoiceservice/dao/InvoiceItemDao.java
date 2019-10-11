package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    // Basic CRUD
    InvoiceItem addInvoiceItem(InvoiceItem item);

    InvoiceItem getInvoiceItem(int id);

    List<InvoiceItem> getAllItems();

    void updateItem(InvoiceItem invoiceItem);

    void deleteItem(int id);

    // Custom Method
    List<InvoiceItem> getItemsByInvoiceId(int invoiceId);

}

