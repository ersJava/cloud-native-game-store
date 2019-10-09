package com.company.retailapiservice.controller;

import com.company.retailapiservice.viewmodel.InvoiceViewModel;
import com.company.retailapiservice.viewmodel.ProductViewModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RetailController {


    @RequestMapping(value = "retail/invoices", method = RequestMethod.POST)
    public InvoiceViewModel submitInvoice(@RequestBody InvoiceViewModel invoice) {
        return null;
    }

    @RequestMapping(value = "retail/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "retail/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        return null;
    }

    @RequestMapping(value = "retail/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "retail/products/inventory", method = RequestMethod.GET)
    public List<ProductViewModel> getProductsInInventory() {
        return null;
    }

    @RequestMapping(value = "retail/products/inventory/{id}", method = RequestMethod.GET)
    public ProductViewModel getProductByInventoryId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "retail/products/{id}", method = RequestMethod.GET)
    public ProductViewModel getProductById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int id) {
        return 0;
    }

}
