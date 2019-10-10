package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceService {

    //uri: /invoice
    //Create invoice registry
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel ivm);

    //Get all invoices
    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    List<InvoiceViewModel> getAllInvoices();

    //Update an Invoice
    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
    void updateInvoice(@RequestBody InvoiceViewModel ivm);

    //uri: /invoice/{id}
    //Get an Invoice
    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable int id);

    //Delete an invoice
    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    void deleteInvoice(@PathVariable int id);

}
