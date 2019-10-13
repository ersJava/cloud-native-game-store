package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.CustomerNotFoundException;
import com.company.adminapiservice.exception.InvoiceNotFoundException;
import com.company.adminapiservice.exception.OutOfStockException;
import com.company.adminapiservice.exception.ProductNotFoundException;
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
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    @Autowired
    public ServiceLayer(CustomerService customerService, InventoryService inventoryService, InvoiceService invoiceService,
                        LevelUpService levelUpService, ProductService productService){

        this.customerService = customerService;
        this.inventoryService = inventoryService;
        this.invoiceService = invoiceService;
        this.levelUpService = levelUpService;
        this.productService = productService;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////INVOICE METHODS///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /invoices
    //Create an Invoice
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
        List<InvoiceItemViewModel> invoiceItemsList = new ArrayList<>();

        //Order Total
        double total = 0;

        //List that stores the Inventory register that have to be updated
        List<InventoryViewModel> inventoriesToUpdate = new ArrayList<>();

        //Getting all the Inventory Rows for the productId
        for(int i = 0; i < productsToBuy.size(); i++){

            ProductToBuyViewModel product = productsToBuy.get(i);

            int productId = product.getProductId();

            //Checking that the product exist
            try{
                productService.getProduct(productId);
            }catch (RuntimeException e){
                throw new ProductNotFoundException("Cannot process your order, there is no Product associated with id number "+ productId+ " in the database!!");
            }

            //Reading the product Price from the ProductService
            BigDecimal unitPrice = BigDecimal.valueOf(Double.valueOf(productService.getProduct(productId).getListPrice()));

            //Reading the Product in Stock from the InventoryService
            List<InventoryViewModel> inventoryList = inventoryService.getAllInventoriesByProductId(product.getProductId());

            inventoryList = orderInventoryListByQuantity(inventoryList);

            int totalInInventory = 0;

            for (int i2 = 0; i2 < inventoryList.size(); i2++){
                int quantityInInventory = inventoryList.get(i2).getQuantity();
                totalInInventory = totalInInventory + quantityInInventory;
            }

            int quantityToBuy = product.getQuantity();

            //Check if there is enough productsToBuy in the inventory.
            if(quantityToBuy > totalInInventory){
                throw new OutOfStockException("Sorry, we can not process your order there is only "+ totalInInventory +" units of the Product with the id " + product.getProductId());
            }

            //List<InvoiceItemViewModel> invoiceItemsPerProduct = updateInventory(inventoryList, quantityToBuy);

            List<InvoiceItemViewModel> invoiceItemsPerProduct = new ArrayList<>();

            for(int i3 = 0; i3 < inventoryList.size(); i3++){

                InventoryViewModel inventory = inventoryList.get(i3);
                int quantityAvailablePerInventory = inventory.getQuantity();

                //Create an InvoiceItem
                InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();

                if (quantityToBuy > quantityAvailablePerInventory) {

                    quantityToBuy = quantityToBuy - quantityAvailablePerInventory;

                    //If the quantityToBuy is larger than the quantity available, that means that is going to empty that Inventory register
                    inventory.setQuantity(0);

                    //Updating the inventory
                    //inventoryService.updateInventory(inventory.getInventoryId(), inventory);
                    inventoriesToUpdate.add(inventory);

                    //Updating the invoiceItem
                    invoiceItem.setQuantity(quantityAvailablePerInventory);
                    invoiceItem.setInventoryId(inventory.getInventoryId());

                    //Adding the invoice to the List
                    invoiceItemsPerProduct.add(invoiceItem);

                } else {

                    inventory.setQuantity(quantityAvailablePerInventory - quantityToBuy);

                    //updating the inventory
                    //inventoryService.updateInventory(inventory.getInventoryId(), inventory);  //Uncomment this
                    inventoriesToUpdate.add(inventory);

                    //updating the invoiceItem
                    invoiceItem.setQuantity(quantityToBuy);
                    invoiceItem.setInventoryId(inventory.getInventoryId());

                    //Adding the invoice to the List
                    invoiceItemsPerProduct.add(invoiceItem);

                    //to break the loop
                    i3 = inventoryList.size();
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            //Setting the Price and for every InvoiceItem
            invoiceItemsPerProduct.stream().forEach(invoiceItem -> invoiceItem.setUnitPrice(unitPrice));

            //Calculating the total of the Order
            for (InvoiceItemViewModel invoiceItem : invoiceItemsPerProduct) {

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

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Creating and saving the Invoice
        //Create a new Invoice
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setCustomerId(ovm.getCustomerId());
        invoice.setPurchaseDate(ovm.getPurchaseDate());
        invoice.setItemList(invoiceItemsList);

        //Creates an Invoice using the microService
        try{
            invoice = invoiceService.createInvoice(invoice);
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException(e.getMessage());
        }

        //Adding the invoice to the purchase OrderViewModel
        ovm.setInvoice(invoice);
        ovm.setProductsToBuy(productsToBuy);


        //Updating the Inventory
        //IMPLEMENT HERE A QUEUE
        for (InventoryViewModel inventory: inventoriesToUpdate) {
            inventoryService.updateInventory(inventory.getInventoryId(), inventory);
        }

        return ovm;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Get all Invoice(s)
    public List<InvoiceViewModel> getAllInvoices(){

        try{
            return invoiceService.getAllInvoices();
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("The database is empty!!! No Invoice(s) found in the Database");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /invoices/{id}
    //Get an Invoice for the Id
    public InvoiceViewModel getInvoice(int id){

        try{
            return invoiceService.getInvoice(id);
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("No Invoice found in the Database for id " + id + "!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Delete an Invoice
    public void deleteInvoice(int id){
        invoiceService.deleteInvoice(id);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /invoices/customer/{customerId}
    public List<InvoiceViewModel> getInvoicesByCustomerId(int customerId){

        try{
            return invoiceService.getInvoicesByCustomerId(customerId);
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("No Invoice(s) found for customer id " + customerId);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////Helper methods for process an INVOICE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
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

    //Update inventory and create InvoiceItems
    public List<InvoiceItemViewModel> updateInventory(List<InventoryViewModel> inventoryList, int quantityToBuy){

        List<InvoiceItemViewModel> invoiceItemsToReturn = new ArrayList<>();

        for(int i = 0; i < inventoryList.size(); i++){

            InventoryViewModel inventory = inventoryList.get(i);
            int quantityAvailable = inventory.getQuantity();

            //Create an InvoiceItem
            InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();

            if (quantityToBuy > quantityAvailable) {


                int invoiceItemQuantity = quantityAvailable;

                quantityToBuy = quantityToBuy - quantityAvailable;

                //If the quantityToBuy is larger than the quantity available, that means that is going to empty that Inventory register
                inventory.setQuantity(0);

                //Updating the inventory
                inventoryService.updateInventory(inventory.getInventoryId(), inventory);  //Uncomment this

                //Updating the invoiceItem
                invoiceItem.setQuantity(invoiceItemQuantity);
                invoiceItem.setInventoryId(inventory.getInventoryId());

                //Adding the invoice to the List
                invoiceItemsToReturn.add(invoiceItem);

            } else {

                inventory.setQuantity(quantityAvailable - quantityToBuy);

                //updating the inventory
                inventoryService.updateInventory(inventory.getInventoryId(), inventory);  //Uncomment this

                //updating the invoiceItem
                invoiceItem.setQuantity(quantityToBuy);
                invoiceItem.setInventoryId(inventory.getInventoryId());

                //Adding the invoice to the List
                invoiceItemsToReturn.add(invoiceItem);

                //to break the loop
                i = inventoryList.size();
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
                    ivmList.remove(i);
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