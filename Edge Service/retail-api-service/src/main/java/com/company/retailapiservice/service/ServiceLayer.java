package com.company.retailapiservice.service;

import com.company.retailapiservice.exception.CustomerNotFoundException;
import com.company.retailapiservice.exception.InventoryNotFoundException;
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
            InvoiceViewModel ivm = new InvoiceViewModel();
            ivm.setCustomerId(orderForm.getCustomerId());
            ivm.setPurchaseDate(orderForm.getPurchaseDate());
            ivm = invoiceClient.createInvoice(ivm);
            orderForm.setInvoiceId(ivm.getInvoiceId());

            // puts all InvoiceItems from orderForm input in a list to check if inventory items exist and available
            List<InvoiceItemViewModel> list = orderForm.getItemList();

            Double total = 0.0;

            list.forEach(itemFromOrder -> {

                InventoryViewModel checkInventory = inventoryClient.getInventory(itemFromOrder.getInventoryId());

                // check if inventory id exist
                if (checkInventory == null)
                    throw new InventoryNotFoundException(String.format("Inventory not found in the system with inventory id %s", itemFromOrder.getInventoryId()));

                // if yes, then check if quantity if available
                if (checkInventory.getQuantity() < itemFromOrder.getQuantity())
                    throw new QuantityNotAvailableException(String.format("Quantity not available for inventory item # %s", itemFromOrder.getInventoryId()));

                // update quantity in database
                checkInventory.setQuantity(checkInventory.getQuantity() - itemFromOrder.getQuantity());
                inventoryClient.updateInventory(checkInventory.getInventoryId(), checkInventory);

                // if yes, get list_price from product table by product
                ProductViewModel product = productClient.getProduct(checkInventory.getProductId());
                itemFromOrder.setUnitPrice(new BigDecimal(product.getListPrice()));

                // setting invoiceId to Item from Invoice
                itemFromOrder.setInvoiceId(orderForm.getInvoiceId());
                // sending orderForm item to invoice client to save to database and to generate item id
                invoiceClient.createItem(itemFromOrder);

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

            // set total
            orderForm.setOrderTotal(new BigDecimal(total));

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
}

