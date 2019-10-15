package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.*;
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

<<<<<<< HEAD
        int count = 0;

        //The deletion is going to be processed if the Customer does not have any related invoices
        try{
            invoiceService.getInvoicesByCustomerId(id);
            count++;
        }catch (RuntimeException e){
            try{
                levelUpService.getLevelUpAccountByCustomerId(id);
                count++;
=======
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
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

            for (int i2 = 0; i2 < inventoryList.size(); i2++){
                int quantityInInventory = inventoryList.get(i2).getQuantity();
                totalInInventory = totalInInventory + quantityInInventory;
            }
<<<<<<< HEAD
        }

        if(count != 0){
            throw new DeleteNotAllowedException("Impossible Deletion, there is LevelUp Account associated with this Customer");
        }

    }
=======
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

            int quantityToBuy = product.getQuantity();

            //Check if there is enough productsToBuy in the inventory.
            if(quantityToBuy > totalInInventory){
                if(totalInInventory == 0){
                    throw new OutOfStockException("Sorry, we can not process your order the Product with the id "+product.getProductId() +" is out of stock!!");
                }else{
                    throw new OutOfStockException("Sorry, we can not process your order there is only "+ totalInInventory +" units of the Product with the id " + product.getProductId());
                }
            }

            //List<InvoiceItemViewModel> invoiceItemsPerProduct = updateInventory(inventoryList, quantityToBuy);

            List<InvoiceItemViewModel> invoiceItemsPerProduct = new ArrayList<>();

<<<<<<< HEAD
        return inventoryService.createInventory(ivm);
    }
=======
            for(int i3 = 0; i3 < inventoryList.size(); i3++){
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

                InventoryViewModel inventory = inventoryList.get(i3);
                int quantityAvailablePerInventory = inventory.getQuantity();

<<<<<<< HEAD
        try{
            return inventoryService.getAllInventories();
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("The database is empty!!! No Inventory found in the Database");
        }
    }
=======
                //Create an InvoiceItem
                InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

                if (quantityToBuy > quantityAvailablePerInventory) {

<<<<<<< HEAD
        try{
            inventoryService.updateInventory(id,ivm);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("Impossible Update, No Inventory found in the Database for id " + id + "!");
        }
    }
=======
                    quantityToBuy = quantityToBuy - quantityAvailablePerInventory;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

                    //If the quantityToBuy is larger than the quantity available, that means that is going to empty that Inventory register
                    inventory.setQuantity(0);

<<<<<<< HEAD
        try{
            return inventoryService.getInventory(id);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("No Inventory found in the Database for id " + id + "!");
        }
    }

    //Delete Inventory
    public void deleteInventory(int id){
        inventoryService.deleteInventory(id);
    }
=======
                    //Updating the inventory
                    //inventoryService.updateInventory(inventory.getInventoryId(), inventory);
                    inventoriesToUpdate.add(inventory);

                    //Updating the invoiceItem
                    invoiceItem.setQuantity(quantityAvailablePerInventory);
                    invoiceItem.setInventoryId(inventory.getInventoryId());
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

                    //Adding the invoice to the List
                    invoiceItemsPerProduct.add(invoiceItem);

<<<<<<< HEAD
        try{
            return inventoryService.getAllInventoriesByProductId(productId);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("No inventories found for productId " + productId + " !");
=======
                } else {

                    inventory.setQuantity(quantityAvailablePerInventory - quantityToBuy);

                    //updating the inventory
                    //inventoryService.updateInventory(inventory.getInventoryId(), inventory);
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

            //Setting the Price for every InvoiceItem
            invoiceItemsPerProduct.stream().forEach(invoiceItem -> invoiceItem.setUnitPrice(unitPrice));

            //Calculating the total of the Order
            for (InvoiceItemViewModel invoiceItem : invoiceItemsPerProduct) {

                total = total + (invoiceItem.getQuantity() * invoiceItem.getUnitPrice().doubleValue());

                //Adding each InvoiceItem inside the InvoiceItemsPerProduct to the List containing all InvoiceItems
                invoiceItemsList.add(invoiceItem);
            }
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
        }

        //Setting the order total
        ovm.setOrderTotal(total);

        //Calculating the points
        int points = (int)(total / 50) * 10;

        int existingPoints = 0;

        //Variables for store error message from the microservices
        String serviceCause = "";
        String errorMessage;

        LevelUpViewModel levelUp = new LevelUpViewModel();

        InvoiceViewModel invoice = new InvoiceViewModel();

        try{
            //Checking if the Customer have a LevelUp Account
            try{
                levelUp = levelUpService.getLevelUpAccountByCustomerId(ovm.getCustomerId());

                existingPoints = levelUp.getPoints();

                levelUp.setPoints(existingPoints + points);

                ovm.setPointsEarned(points);
                ovm.setTotalPoints(existingPoints + points);

            }catch (RuntimeException e){
                //When the customer is not a member of the points program
                ovm.setPointsEarned(0);
                ovm.setTotalPoints(0);
            }

            //Updating the points for the Customer in his account
            if(levelUp != null){
                try{
                    levelUpService.updatePointsOnAccount(ovm.getCustomerId(),levelUp);
                }catch (RuntimeException e){
                    serviceCause = "levelupService";
                    throw new IllegalArgumentException("Impossible to process order, error : " + e.getMessage()+ " happen when trying to update the LevelUp Account of the user");
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Creating and saving the Invoice
            //Create a new Invoice
            invoice.setInvoiceId(null);
            invoice.setCustomerId(ovm.getCustomerId());
            invoice.setPurchaseDate(ovm.getPurchaseDate());
            invoice.setItemList(invoiceItemsList);

            //Creates an Invoice using the microService
            try{
                invoice = invoiceService.createInvoice(invoice);
            }catch (RuntimeException e){
                serviceCause = "invoiceService";
                throw new IllegalArgumentException("Impossible to process order, error : " + e.getMessage()+ " happen when trying to create an Invoice register for the order");
            }

            //Adding the invoice to the purchase OrderViewModel
            ovm.setInvoice(invoice);
            ovm.setProductsToBuy(productsToBuy);

            //Updating the Inventory
            //IMPLEMENT HERE A QUEUE
            for (InventoryViewModel inventory: inventoriesToUpdate) {
                try{
                    inventoryService.updateInventory(inventory.getInventoryId(), inventory);
                }catch (RuntimeException e){

                    serviceCause = "inventoryService";
                    throw new IllegalArgumentException("Impossible to process order, error : " + e.getMessage()+ " happen when trying to update the Inventory register for the order");
                }
            }

            return ovm;

        }catch (IllegalArgumentException f){
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //This is going to be executed if any of the operations for update the points in a levelUp account, create an Invoice,
            //or update the inventory fail.

            switch (serviceCause){
                case "levelupService":
                    //Roll back
                    //This is the first communication error to happen with a service, so there is nothing to be change/
                    break;

                case "invoiceService":
                    //Roll back
                    //when this error happen the points for the purchase have been already updated so is necessary to reverse that back
                    if(levelUp != null){
                        levelUp.setPoints(existingPoints);
                        levelUpService.updatePointsOnAccount(ovm.getCustomerId(),levelUp);
                    }
                    break;

                case "inventoryService":
                    //Roll back
                    //when this error happen the points in the LevelUpAccount have been updated and the Invoice created, is necessary to reverse
                    //back those two operations

                    //Deleting the just created Invoice
                    invoiceService.deleteInvoice(invoice.getInvoiceId());

                    //Updating the levelUp account with the previous points
                    if(levelUp != null){
                        levelUp.setPoints(existingPoints);
                        levelUpService.updatePointsOnAccount(ovm.getCustomerId(),levelUp);
                    }
                    break;
            }
            errorMessage = f.getMessage();
            throw new OrderProcessFailException(errorMessage);
        }
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

    public List<InventoryViewModel> orderInventoryListByQuantity(List<InventoryViewModel> ivmList){

<<<<<<< HEAD
        int impossibleDelete = 0;
        boolean inStock = false;

        try{
            //Get all the Inventory registers related to the product
            List<InventoryViewModel> inventoryListForProduct = inventoryService.getAllInventoriesByProductId(id);
=======
        List<InventoryViewModel> orderedList = new ArrayList<>();
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

        List<Integer> quantityLargerToLower = new ArrayList<>();

        ivmList.stream().forEach(inventoryViewModel -> quantityLargerToLower.add(inventoryViewModel.getQuantity()));

        quantityLargerToLower.sort(Comparator.reverseOrder());

        int x = 0;

        for(int i = 0; i < ivmList.size(); i++){
            InventoryViewModel ivm = ivmList.get(i);

            if(x < quantityLargerToLower.size()){

<<<<<<< HEAD
                if(count != inventoryListForProduct.size()){
                    impossibleDelete++;
                }else{
                    productService.deleteProduct(id);
=======
                if(ivm.getQuantity() == quantityLargerToLower.get(x)){
                    orderedList.add(ivm);
                    ivmList.remove(i);
                    x++;
                    i = -1;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
                }
            }else{
<<<<<<< HEAD
                impossibleDelete++;
                inStock = true;
=======
                i = ivmList.size();
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
            }
        }
<<<<<<< HEAD


        if(impossibleDelete != 0){
            if(inStock){
                throw new DeleteNotAllowedException("Impossible Deletion, there is Products still in inventory");
            }else{
                throw new DeleteNotAllowedException("Impossible Deletion, this products have associated InvoiceItems");
            }

        }

=======
        return orderedList;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
    }
}

//Update inventory and create InvoiceItems
//    public List<InvoiceItemViewModel> updateInventory(List<InventoryViewModel> inventoryList, int quantityToBuy){
//
//        List<InvoiceItemViewModel> invoiceItemsToReturn = new ArrayList<>();
//
//        for(int i = 0; i < inventoryList.size(); i++){
//
//            InventoryViewModel inventory = inventoryList.get(i);
//            int quantityAvailable = inventory.getQuantity();
//
//            //Create an InvoiceItem
//            InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();
//
//            if (quantityToBuy > quantityAvailable) {
//
//
//                int invoiceItemQuantity = quantityAvailable;
//
//                quantityToBuy = quantityToBuy - quantityAvailable;
//
//                //If the quantityToBuy is larger than the quantity available, that means that is going to empty that Inventory register
//                inventory.setQuantity(0);
//
//                //Updating the inventory
//                inventoryService.updateInventory(inventory.getInventoryId(), inventory);  //Uncomment this
//
//                //Updating the invoiceItem
//                invoiceItem.setQuantity(invoiceItemQuantity);
//                invoiceItem.setInventoryId(inventory.getInventoryId());
//
//                //Adding the invoice to the List
//                invoiceItemsToReturn.add(invoiceItem);
//
//            } else {
//
//                inventory.setQuantity(quantityAvailable - quantityToBuy);
//
//                //updating the inventory
//                inventoryService.updateInventory(inventory.getInventoryId(), inventory);  //Uncomment this
//
//                //updating the invoiceItem
//                invoiceItem.setQuantity(quantityToBuy);
//                invoiceItem.setInventoryId(inventory.getInventoryId());
//
//                //Adding the invoice to the List
//                invoiceItemsToReturn.add(invoiceItem);
//
//                //to break the loop
//                i = inventoryList.size();
//            }
//        }
//
//
//        return invoiceItemsToReturn;
//    }