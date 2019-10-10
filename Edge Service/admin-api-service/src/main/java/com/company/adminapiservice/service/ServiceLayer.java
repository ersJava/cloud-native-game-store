package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.CustomerNotFoundException;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class ServiceLayer {

    CustomerService customerService;
    InventoyService inventoyService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    @Autowired
    public ServiceLayer(CustomerService customerService, InventoyService inventoyService, InvoiceService invoiceService,
                        LevelUpService levelUpService, ProductService productService){

        this.customerService = customerService;
        this.inventoyService = inventoyService;
        this.invoiceService = invoiceService;
        this.levelUpService = levelUpService;
        this.productService = productService;
    }

    //uri: /order
    @Transactional
    public OrderViewModel processOrder(OrderViewModel ovm){

        //List to store the invoiceItems
        List<InvoiceItem> invoiceItems = ovm.getInvoiceItems();

        //List with uniques elements
        List<InvoiceItem> filteredInvoiceItems = filterInvoiceItemList(invoiceItems);

        //Making sure that the Customer exist
        try{
            customerService.getCustomer(ovm.getCustomerId());
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("No Customer found in the database for id #" + ovm.getCustomerId() + " !");
        }

        //Check that there is enough of the product in the inventory, and update the inventory
        for (InvoiceItem ii: filteredInvoiceItems) {
            InventoryViewModel ivm = inventoyService.readInventory(ii.getInventoryId());

            if(ii.getQuantity() > ivm.getQuantity()){
                //throw new OutOfStockException();

            }else{
                ivm.setQuantity(ivm.getQuantity() - ii.getQuantity());
                inventoyService.updateInventory(ivm);
            }
        }

        //Reading the unit_price for each product in the invoiceItem
        List<ProductViewModel> products = new ArrayList<>();

        filteredInvoiceItems.stream().forEach(invoiceItem ->
                products.add(productService.getProduct(inventoyService.readInventory(invoiceItem.getInventoryId()).getProductId())));

        //Adding the price for every InvoiceItem
        for(int i = 0; i < filteredInvoiceItems.size(); i++){
            filteredInvoiceItems.get(i).setUnitPrice(BigDecimal.valueOf(Double.valueOf(products.get(i).getListPrice())));
        }

        //Calculating the total
        double total = 0;

        for (InvoiceItem invoiceItem: filteredInvoiceItems) {
            total = total + (invoiceItem.getQuantity() * invoiceItem.getUnitPrice().doubleValue());
        }

        //Setting the order total
        ovm.setOrderTotal(total);

        //Calculating the points
        int points = (int)(total / 50) * 10;

        try{
            LevelUpViewModel levelUp = levelUpService.getLevelUpAccountByCustomerId(ovm.getCustomerId());
            int existingPoints = levelUp.getPoints();
            levelUp.setPoints(existingPoints + points);

            //Updating the points for the Customer
            levelUpService.updatePointsOnAccount(ovm.getCustomerId(),levelUp);

            ovm.setPointsEarned(points);
            ovm.setTotalPoints(existingPoints + points);

        }catch (RuntimeException e){
            //When the customer is not a member of the points program
            ovm.setPointsEarned(0);
            ovm.setTotalPoints(0);
        }

        //Create a new Invoice
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setCustomerId(ovm.getCustomerId());
        invoice.setPurchaseDate(ovm.getPurchaseDate());
        invoice.setInvoiceItems(filteredInvoiceItems);

        //Creates an Invoice using the microService
        invoice = invoiceService.createInvoice(invoice);


        return ovm;

    }

    //Helper method
    public List<InvoiceItem> filterInvoiceItemList(List<InvoiceItem> itemList){

        //List with uniques inventoryId
        Set<Integer> inventoryId = new HashSet<>();

        for(int i = 0; i < itemList.size(); i++){

            InvoiceItem iItem = itemList.get(i);
            Integer inId = iItem.getInventoryId();

            if(inventoryId.add(inId) == false){

                int quantity = iItem.getQuantity();

                for(int x = 0; x < itemList.size(); x++){

                    InvoiceItem i2 = itemList.get(x);

                    if(i2.getInventoryId() == inId){

                        i2.setQuantity(i2.getQuantity() + quantity);
                        itemList.remove(i);

                        //To break the loop
                        x = itemList.size();
                    }
                }
                i = -1;
                inventoryId.clear();
            }
        }
        return itemList;
    }
}
