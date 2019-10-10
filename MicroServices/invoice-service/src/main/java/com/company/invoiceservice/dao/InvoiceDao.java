package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    // Basic CRUD
    Invoice addInvoice(Invoice invoice);

    Invoice getInvoice(int id);

    List<Invoice> getAllInvoices();

    void deleteInvoice(int id);

    // Custom Method
    List<Invoice> getInvoicesByCustomerId(int customerId);

}
