package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.InvoiceItemViewModel;
import com.company.adminapiservice.viewmodel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceService {

    //uri: /invoices
    //Create invoice registry
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel ivm);

    //Get all invoices
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    List<InvoiceViewModel> getAllInvoices();

    //Update an Invoice
    @RequestMapping(value = "/invoices", method = RequestMethod.PUT)
    void updateInvoice(@RequestBody InvoiceViewModel ivm);

    //uri: /invoices/{id}
    //Get an Invoice
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable int id);

    //Delete an invoice
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    void deleteInvoice(@PathVariable int id);

    //uri: /invoices/customer/{customerId}
    //Get Invoice(s) by customerId
    @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
    List<InvoiceViewModel> getInvoicesByCustomerId (@PathVariable int customerId);

    //uri: /invoices/inventory/{inventoryId}
    //Get InvoiceItem(s) by inventoryId
    @RequestMapping(value = "invoices/inventory/{inventoryId}")
    public List<InvoiceItemViewModel> getInvoiceItemsByInventoryId(@PathVariable("inventoryId") Integer inventoryId);

}
