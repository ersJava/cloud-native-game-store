package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.CustomerNotFoundException;
import com.company.retailapiservice.exception.InventoryNotFoundException;
import com.company.retailapiservice.exception.InvoiceNotFoundException;
import com.company.retailapiservice.exception.QuantityNotAvailableException;
import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public OrderForm submitOrderForm(OrderForm orderForm) {

        // check to see if Customer Id exists
        if(customerClient.getCustomer(orderForm.getCustomerId()) == null)
            throw new CustomerNotFoundException(String.format("No Customer found in the database for id # %s", orderForm.getCustomerId()));

            // creates an InvoiceViewModel with the OrderForm information from requestBody input, then creating IVM in invoice client
            InvoiceViewModel invoice = new InvoiceViewModel();
            invoice.setCustomerId(orderForm.getCustomerId());
            invoice.setPurchaseDate(orderForm.getPurchaseDate());
            invoice.setItemList(orderForm.getItemList());
            orderForm.setInvoiceId(invoice.getInvoiceId());

            // puts all InvoiceItems from orderForm input in a list to check if inventory items exist and available
            List<InvoiceItemViewModel> list = orderForm.getItemList();

            Double total = 0.0;

            list.forEach(item -> {

                // Retrieving inventory by item input by inventory ID
                InventoryViewModel checkInventory;

                try {
                    // Retrieving inventory by item input by inventory ID
                   checkInventory = inventoryClient.getInventory(item.getInventoryId());
                } catch (Exception e) {
                    throw new InventoryNotFoundException(String.format("Inventory not found in the system with inventory id %s", item.getInventoryId()));
                }

                // if yes, then check if quantity is available
                if (checkInventory.getQuantity() < item.getQuantity())
                    throw new QuantityNotAvailableException(String.format("Quantity not available for inventory item # %s", item.getInventoryId()));

                // update quantity in database
                checkInventory.setQuantity(checkInventory.getQuantity() - item.getQuantity());
                inventoryClient.updateInventory(checkInventory.getInventoryId(), checkInventory);

                // Creating a new ItemViewModel to send to Client to store item input so we can return with ID
               // if quantity is available we get list_price from product table by product ID on Inventory table
                ProductViewModel product = productClient.getProduct(checkInventory.getProductId());
                item.setUnitPrice(new BigDecimal(product.getListPrice()));

            });

        // do calculation and add to total
            for(InvoiceItemViewModel i : list) {

                BigDecimal price = i.getUnitPrice();
                Integer quantity = i.getQuantity();
                Double priceAsDouble = price.doubleValue();

                Double totalItemPrice = quantity * priceAsDouble;

                total += totalItemPrice;

            }

            // set ItemList with prices and ids now
            orderForm.setItemList(list);

            invoice = invoiceClient.createInvoice(invoice);

            // set total
            orderForm.setOrderTotal(new BigDecimal(total));

            // set id
            orderForm.setInvoiceId(invoice.getInvoiceId());

            // Add Level Up points to Customer Level Up Account
        orderForm.setLevelUpPointsEarned(getLevelUpPointsForOrder(new BigDecimal(total), orderForm.getCustomerId()));

        return orderForm;

        }

        // Helper Methods for Order Form
    private int getLevelUpPointsForOrder(BigDecimal orderTotal, int customerId) {

        // calculate the total of points earned for order
        int totalToCalculate = orderTotal.intValue();

        // every $50 earns 10 pts
        int fifty = totalToCalculate/50;
        int totalPoints = fifty * 10;

        // update Customer level up account
        LevelUpViewModel account = levelUpClient.getLevelUpAccountByCustomerId(customerId);
        account.setPoints(totalPoints);
        levelUpClient.updatePointsOnAccount(account.getCustomerId(), account);

        return totalPoints;

    }

    // - - - - - - - - Invoice client methods - - - - - - - -
    InvoiceViewModel addInvoice(InvoiceViewModel invoiceViewModel) {

        try {
            customerClient.getCustomer(invoiceViewModel.getCustomerId());
        } catch (Exception e) {
            throw new CustomerNotFoundException(String.format("No Customer found in the database for id # %s", invoiceViewModel.getCustomerId()));
        }

        List<InvoiceItemViewModel> list = invoiceViewModel.getItemList();

        list.forEach(item -> {

            // Retrieving inventory by item input by inventory ID
            InventoryViewModel checkInventory = inventoryClient.getInventory(item.getInventoryId());

            // Retrieving inventory by item input by inventory ID
            if (checkInventory == null)
                throw new InventoryNotFoundException(String.format("Inventory not found in the system with inventory id %s", item.getInventoryId()));

            if (checkInventory.getQuantity() < item.getQuantity())
                throw new QuantityNotAvailableException(String.format("Quantity not available for inventory item # %s", item.getInventoryId()));

            // update quantity in database
            checkInventory.setQuantity(checkInventory.getQuantity() - item.getQuantity());
            inventoryClient.updateInventory(checkInventory.getInventoryId(), checkInventory);

            ProductViewModel product = productClient.getProduct(checkInventory.getProductId());
            item.setUnitPrice(new BigDecimal(product.getListPrice()));
                });

        // this list now has checked the inventory for available
        // quantity and has retrieved the price from the product table
        invoiceViewModel.setItemList(list);

        // now we can save with all the information and get back an InvoiceViewModel with ids
        invoiceViewModel = invoiceClient.createInvoice(invoiceViewModel);

        return invoiceViewModel;
    }

    InvoiceViewModel findInvoice(Integer id) {

        InvoiceViewModel ivm = invoiceClient.getInvoice(id);

        if(ivm == null)
            throw new InvoiceNotFoundException(String.format("Invoice could not be retrieved for id %s", id));
        else
            return ivm;
    }
}

