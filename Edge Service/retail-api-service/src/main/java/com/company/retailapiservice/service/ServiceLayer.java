package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.CustomerNotFoundException;
import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewmodel.InvoiceItem;
import com.company.retailapiservice.viewmodel.InvoiceItemViewModel;
import com.company.retailapiservice.viewmodel.InvoiceViewModel;
import com.company.retailapiservice.viewmodel.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    private CustomerClient customerClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, InventoryClient inventoryClient, InvoiceClient invoiceClient,
                        LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }

    public OrderForm submitOrderForm(OrderForm orderForm) {

        // Check to see if Customer Id exists
        try {
            customerClient.getCustomer(orderForm.getCustomerId());
        } catch (RuntimeException e) {
            throw new CustomerNotFoundException(String.format("No Customer found in the database for id # %s", orderForm.getCustomerId()));
        }

        // creates a InvoiceViewModel with the OrderForm information from requestBody input, then creating IVM in invoice client
       InvoiceViewModel ivm = new InvoiceViewModel();
       ivm.setCustomerId(orderForm.getCustomerId());
       ivm.setPurchaseDate(orderForm.getPurchaseDate());
       ivm = invoiceClient.createInvoice(ivm);
       orderForm.setInvoiceId(ivm.getInvoiceId());

       List<InvoiceItem> list = orderForm.getItemList();

       list.forEach(item -> {
           item.setInvoiceId(orderForm.getInvoiceId());

       })




       // Add Level Up points to Customer Level Up Account


    }

}
