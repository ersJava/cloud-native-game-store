package com.company.retailapiservice.controller;

import com.company.retailapiservice.service.ServiceLayer;
import com.company.retailapiservice.viewmodel.InvoiceViewModel;
import com.company.retailapiservice.viewmodel.OrderForm;
import com.company.retailapiservice.viewmodel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RetailController {

    @Autowired
    private ServiceLayer serviceLayer;


    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public OrderForm submitInvoice(@RequestBody OrderForm orderForm) {
        return serviceLayer.submitOrderForm(orderForm);
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        return null;
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<ProductViewModel> getProductsInInventory() {
        return null;
    }

    @RequestMapping(value = "/products/inventory/{id}", method = RequestMethod.GET)
    public ProductViewModel getProductByInventoryId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductViewModel getProductById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int id) {
        return 0;
    }

}
