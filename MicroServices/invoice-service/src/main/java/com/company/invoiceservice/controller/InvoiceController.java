package com.company.invoiceservice.controller;

import com.company.invoiceservice.service.ServiceLayer;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoice) {

        return serviceLayer.saveInvoice(invoice);
    }

    // Read all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {

        return serviceLayer.findAllInvoices();
    }

    // Read by Id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") Integer id) {

        return serviceLayer.findInvoice(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") Integer id) {

        serviceLayer.removeInvoice(id);
    }

    // Custom method - find invoice by customer Id
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable("customerId") Integer customerId) {

        return serviceLayer.findInvoicesByCustomerId(customerId);

    }
}
