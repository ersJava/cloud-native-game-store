package com.company.invoiceservice.controller;

import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.service.ServiceLayer;
import com.company.invoiceservice.viewmodel.InvoiceItemViewModel;
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
    private ServiceLayer serviceLayer;

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

    // Update - can add/update/delete InvoiceItems on InvoiceViewModel
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice (@PathVariable("id") Integer id, @RequestBody InvoiceViewModel invoice) {

        if (!id.equals(invoice.getInvoiceId())) {
            throw new IllegalArgumentException(String.format("Id %s in the PathVariable does not match the Id %s in the RequestBody ", id, invoice.getInvoiceId()));
        }
        serviceLayer.updateInvoice(invoice);
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

    // Create Item - used only to build Invoice
    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItemViewModel createItem(@RequestBody @Valid InvoiceItemViewModel item) {

        return serviceLayer.saveItem(item);
    }
  
    //Get Invoice Items by inventoryId
    @GetMapping("/inventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getInvoiceItemsByInventoryId(@PathVariable("inventoryId") Integer inventoryId) {

        return serviceLayer.getInvoiceItemByInventoryId(inventoryId);
    }
}
