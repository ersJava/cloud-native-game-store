package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.viewmodel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient("invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoice);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable("id") Integer id);

    @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
    List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable("customerId") Integer customerId);

}
