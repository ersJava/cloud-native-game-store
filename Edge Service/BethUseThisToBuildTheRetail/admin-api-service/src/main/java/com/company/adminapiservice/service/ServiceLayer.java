package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.CustomerNotFoundException;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


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

        //Making sure that the Customer exist
        try{
            customerService.getCustomer(ovm.getCustomerId());
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("No Customer found in the database for id #" + ovm.getCustomerId() + " !");
        }

        //List to store the ProductsToBuy
        List<ProductToBuyViewModel> productsToBuy = ovm.getProductsToBuy();

        //List with uniques ProductsToBuy
        productsToBuy = filterProductsToBuyList(productsToBuy);

        //List InvoiceITems
        List<InvoiceItem> invoiceItemsList = new ArrayList<>();

        //Order Total
        double total = 0;

        //Getting all the Inventory Rows for the productId
        for(int i = 0; i < productsToBuy.size(); i++){

            ProductToBuyViewModel product = productsToBuy.get(i);

            int productId = product.getProductId();

            //Reading the product Price from the ProductService
            BigDecimal unitPrice = BigDecimal.valueOf(Double.valueOf(productService.getProduct(productId).getListPrice()));

            //Reading the Product in Stock from the InventoryService
            List<InventoryViewModel> inventoryList = inventoyService.getAllInventoriesByProductId(product.getProductId());

            inventoryList = orderInventoryListByQuantity(inventoryList);

            int quantityToBuy = product.getQuantity();

            //Check if there is enough productsToBuy in the inventory.
            List<InvoiceItem> invoiceItemsPerProduct = updateInventory(inventoryList, quantityToBuy);

            //Setting the Price and for every InvoiceItem
            invoiceItemsPerProduct.stream().forEach(invoiceItem -> invoiceItem.setUnitPrice(unitPrice));

            //Calculating the total of the Order
            for (InvoiceItem invoiceItem : invoiceItemsPerProduct) {

                total = total + (invoiceItem.getQuantity() * invoiceItem.getUnitPrice().doubleValue());

                //Adding each InvoiceItem inside the InvoiceItemsPerProduct to the List containing all InvoiceItems
                invoiceItemsList.add(invoiceItem);
            }

        }

        //Setting the order total
        ovm.setOrderTotal(total);

        //Calculating the points
        int points = (int)(total / 50) * 10;

        //Checking if the Customer have a LevelUp Account
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

        //Creating and saving the Invoice

        //Create a new Invoice
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setCustomerId(ovm.getCustomerId());
        invoice.setPurchaseDate(ovm.getPurchaseDate());
        invoice.setInvoiceItems(invoiceItemsList);

        //Creates an Invoice using the microService
        invoice = invoiceService.createInvoice(invoice);

        //Adding the invoice to the purchase OrderViewModel
        ovm.setInvoice(invoice);
        ovm.setProductsToBuy(productsToBuy);

        return ovm;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////Helper methods for process and OrderViewModel\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<ProductToBuyViewModel> filterProductsToBuyList(List<ProductToBuyViewModel> productList){

        List<ProductToBuyViewModel> filteredList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ProductToBuyViewModel product = productList.get(i);

            ProductToBuyViewModel newProduct = new ProductToBuyViewModel();
            newProduct.setProductId(product.getProductId());
            newProduct.setQuantity(product.getQuantity());

            filteredList.add(newProduct);
        }

        //List with uniques inventoryId
        Set<Integer> productId = new HashSet<>();

        for(int i = 0; i < filteredList.size(); i++){

            ProductToBuyViewModel product = filteredList.get(i);
            Integer inId = product.getProductId();

            if(productId.add(inId) == false){

                int quantity = product.getQuantity();

                for(int x = 0; x < filteredList.size(); x++){

                    ProductToBuyViewModel product2 = filteredList.get(x);

                    if(product2.getProductId() == inId){

                        product2.setQuantity(product2.getQuantity() + quantity);
                        filteredList.remove(i);

                        //To break the loop
                        x = filteredList.size();
                    }
                }
                i = -1;
                productId.clear();
            }
        }
        return filteredList;
    }

    public List<InvoiceItem> updateInventory(List<InventoryViewModel> inventoryList, int quantityToBuy){

        List<InvoiceItem> invoiceItemsToReturn = new ArrayList<>();

        int totalInInventory = 0;

        for (int i = 0; i < inventoryList.size(); i++){
            int quantityInInventory = inventoryList.get(i).getQuantity();
            totalInInventory = totalInInventory + quantityInInventory;
        }

        if(quantityToBuy > totalInInventory){
            //throw new OutOfStockException();
        }else{

            for(int i = 0; i < inventoryList.size(); i++){
                InventoryViewModel inventory = inventoryList.get(i);
                int quantityAvailable = inventory.getQuantity();

                //Create an InvoiceItem
                InvoiceItem invoiceItem = new InvoiceItem();

                if(quantityToBuy > quantityAvailable){



                    int invoiceItemQuantity = quantityAvailable;

                    quantityToBuy = quantityToBuy - quantityAvailable;

                    //If the quantityToBuy is larger than the quantity available, that means that is going to empty that Inventory register
                    inventory.setQuantity(0);

                    //Updating the inventory
                    inventoyService.updateInventory(inventory);  //Uncomment this

                    //Updating the invoiceItem
                    invoiceItem.setQuantity(invoiceItemQuantity);
                    invoiceItem.setInventoryId(inventory.getInventoryId());

                    //Adding the invoice to the List
                    invoiceItemsToReturn.add(invoiceItem);

                }else{

                    inventory.setQuantity(quantityAvailable - quantityToBuy);

                    //updating the inventory
                    inventoyService.updateInventory(inventory);  //Uncomment this

                    //updating the invoiceItem
                    invoiceItem.setQuantity(quantityToBuy);
                    invoiceItem.setInventoryId(inventory.getInventoryId());

                    //Adding the invoice to the List
                    invoiceItemsToReturn.add(invoiceItem);

                    //to break the loop
                    i = inventoryList.size();
                }
            }
        }

        return invoiceItemsToReturn;
    }

    public List<InventoryViewModel> orderInventoryListByQuantity(List<InventoryViewModel> ivmList){

        List<InventoryViewModel> orderedList = new ArrayList<>();

        List<Integer> quantityLargerToLower = new ArrayList<>();

        ivmList.stream().forEach(inventoryViewModel -> quantityLargerToLower.add(inventoryViewModel.getQuantity()));

        quantityLargerToLower.sort(Comparator.reverseOrder());

        int x = 0;

        for(int i = 0; i < ivmList.size(); i++){
            InventoryViewModel ivm = ivmList.get(i);

            if(x < quantityLargerToLower.size()){

                if(ivm.getQuantity() == quantityLargerToLower.get(x)){
                    orderedList.add(ivm);
                    x++;
                    i = -1;
                }
            }else{
                i = ivmList.size();
            }
        }
        return orderedList;
    }
}
