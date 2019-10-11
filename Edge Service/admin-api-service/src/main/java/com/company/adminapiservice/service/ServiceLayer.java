package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.*;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////CUSTOMER METHODS///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customers
    //Create a new Customer
    @Transactional
    public CustomerViewModel createCustomer(CustomerViewModel cvm){

        return customerService.newCustomer(cvm);
    }

    //Return All the Customer
    public List<CustomerViewModel> getAllCustomers(){
        try{
            return customerService.getAllCustomers();
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("The database is empty!!! No Customer(s) found in the Database");
        }
    }

    //Update a Customer
    @Transactional
    public void updateCustomer(CustomerViewModel cvm){
        try{
            customerService.updateCustomer(cvm);
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("Impossible Update, No Customer found in the database for id #" + cvm.getCustomerId() + " !");
        }
    }

    //uri: /customer/{id}
    //Get a Customer for Id
    public CustomerViewModel getCustomer(int id){
        try{
            return customerService.getCustomer(id);
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("No Customer found in the database for id #" + id + " !");
        }
    }

    //Delete a Customer
    public void deleteCustomer(int id){

        //The deletion is going to be processed if the Customer does not have any related invoices
        try{
            invoiceService.getInvoicesByCustomerId(id);
            throw new DeleteNotAllowedException("Impossible Delete, there is Invoice registers associated with this Customer");
        }catch (RuntimeException e){

            try{
                levelUpService.getLevelUpAccountByCustomerId(id);
                throw new DeleteNotAllowedException("Impossible Deletion, there is LevelUp Account associated with this Customer");

            }catch (RuntimeException f){
                customerService.deleteCustomer(id);
            }
        }
    }

    //uri: /customer/findByLastName/{last_name}
    public List<CustomerViewModel> getCustomersByLastName(String lastName){
        try{
            return customerService.getCustomersByLastName(lastName);
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("No Customer(s) found with the lastName: " + lastName + "!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////INVENTORY METHODS//////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /inventory
    //Create new Inventory Register
    @Transactional
    public InventoryViewModel createInventory(InventoryViewModel ivm){

        //Make sure that the product exists
        try{
            productService.getProduct(ivm.getProductId());
        }catch (RuntimeException e){
            throw new ProductNotFoundException("Creation of Inventory not Allowed, No Product found in the database for id #" +  ivm.getProductId()+ " !");
        }

        return inventoyService.createInventory(ivm);
    }

    //Get all inventories
    public List<InventoryViewModel> getAllInventories(){

        try{
            return inventoyService.getAllInventories();
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("The database is empty!!! No Inventory found in the Database");
        }
    }

    //Update Inventory
    @Transactional
    public void updateInventory(int id, InventoryViewModel ivm){

        try{
            inventoyService.updateInventory(id,ivm);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("Impossible Update, No Inventory found in the Database for id " + id + "!");
        }
    }

    //uri: inventory/{id}
    //Get Inventory for id
    public InventoryViewModel getInventory(int id){

        try{
            return inventoyService.getInventory(id);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("No Inventory found in the Database for id " + id + "!");
        }
    }

    //Delete Inventory
    public void deleteInventory(int id){
        inventoyService.deleteInventory(id);
    }

    //uri: /inventory/byProductId/{id}
    //Get all Inventories by productId
    public List<InventoryViewModel> getAllInventoriesByProductId(int productId){

        try{
            return inventoyService.getAllInventoriesByProductId(productId);
        }catch (RuntimeException e){
            throw new InventoryNotFoundException("No inventories found for productId " + productId + " !");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////INVOICE METHODS///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /invoices
    //Create an Invoice
    @Transactional
    public InvoiceViewModel createInvoice(InvoiceViewModel ivm){

        //Making sure that the Customer specified exist
        try{
            customerService.getCustomer(ivm.getCustomerId());
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("Creation of Invoice not Allowed, No Customer found in the database for id #" +  ivm.getCustomerId()+ " !");
        }

        return invoiceService.createInvoice(ivm);
    }

    //Get all Invoice(s)
    public List<InvoiceViewModel> getAllInvoices(){

        try{
            return invoiceService.getAllInvoices();
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("The database is empty!!! No Invoice(s) found in the Database");
        }
    }

    //uri: /invoices/{id}
    //Get an Invoice for the Id
    public InvoiceViewModel getInvoice(int id){

        try{
            return invoiceService.getInvoice(id);
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("No Invoice found in the Database for id " + id + "!");
        }
    }

    //Delete an Invoice
    public void deleteInvoice(int id){
        invoiceService.deleteInvoice(id);
    }


    //uri: /invoices/customer/{customerId}
    public List<InvoiceViewModel> getInvoicesByCustomerId(int customerId){

        try{
            return invoiceService.getInvoicesByCustomerId(customerId);
        }catch (RuntimeException e){
            throw new InvoiceNotFoundException("No invoices in the system found with customer id " + customerId);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////LEVELUP METHODS//////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /levelup
    //Create a LevelUp
    @Transactional
    public LevelUpViewModel createLevelUp(LevelUpViewModel lvm){

        try{
            customerService.getCustomer(lvm.getCustomerId());
        }catch (RuntimeException e){
            throw new CustomerNotFoundException("Creation of LevelUp not Allowed, No Customer found in the database for id #" +  lvm.getCustomerId()+ " !");
        }

        return levelUpService.createLevelUpAccount(lvm);
    }

    //Get All LevelUp Accounts
    public List<LevelUpViewModel> getAllLevelUpAccounts(){
        try{
            return levelUpService.getAllLevelUpAccounts();
        }catch (RuntimeException e){
            throw new LevelUpNotFoundException("The database is empty!!! No LevelUp Account(s) found in the Database");
        }
    }

    //uri: /levelup/{id}
    //Get a levelUp Account
    public LevelUpViewModel getLevelUpAccount(int id){
        try{
            return levelUpService.getLevelUpAccount(id);
        }catch (RuntimeException e){
            throw new LevelUpNotFoundException("No levelUp Account found in the Database for id " + id + "!");
        }
    }

    //Delete a levelUp Account
    public void deleteLevelUpAccount(int id){
        levelUpService.deleteLevelUpAccount(id);
    }

    //uri: /levelup/points/{customerId}
    //Update points in account for a Customer
    @Transactional
    public void updatePointsInAccount(int customerId, LevelUpViewModel lvm){

        try{
            levelUpService.updatePointsOnAccount(customerId, lvm);
        }catch (RuntimeException e){
            throw new LevelUpNotFoundException("Impossible Update, No LevelUp Account found in the Database for id " + lvm.getLevelUpId() + "!");
        }
    }

    //uri: /levelup/customer/{customerId}
    //Get a LevelUp Account for the customerId
    public LevelUpViewModel getLevelUpAccountByCustomerId(int customerId){
        try{
            return levelUpService.getLevelUpAccountByCustomerId(customerId);
        }catch (RuntimeException e){
            throw new LevelUpNotFoundException("No LevelUp Account in the system found with customer id " + customerId);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////PRODUCT METHODS///////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /product
    //Create a new product
    @Transactional
    public ProductViewModel createProduct(ProductViewModel pvm){
        return productService.newProduct(pvm);
    }

    //Get all Products
    public List<ProductViewModel> getAllProducts(){
        try{
            return productService.getAllProducts();
        }catch (RuntimeException e){
            throw new ProductNotFoundException("The database is empty!!! No Product(s) found in the Database");
        }
    }

    //Update a Product
    public void updateProduct(ProductViewModel pvm){
        try{
            productService.updateProduct(pvm);
        }catch (RuntimeException e){
            throw new ProductNotFoundException("Impossible Update, No LevelUp Account found in the Database for id " + pvm.getProductId() + "!");
        }
    }

    //uri: /product/{id}
    //Get a Product
    public ProductViewModel getProduct(int id){
        try{
            return productService.getProduct(id);
        }catch (RuntimeException e){
            throw new ProductNotFoundException("No Product found in the Database for id " + id + "!");
        }
    }

    //Delete a Product
    public void deleteProduct(int id){

        try{
            //Get all the Inventory registers related to the product
            List<InventoryViewModel> inventoryListForProduct = inventoyService.getAllInventoriesByProductId(id);

            //List that stores the Inventories with a quantity of 0
            List<InventoryViewModel> emptyInventoryForProduct;

            emptyInventoryForProduct = inventoryListForProduct.stream().filter(inventoryViewModel -> inventoryViewModel.getQuantity() == 0).collect(Collectors.toList());

            //List with the id of the Inventories
            List<Integer> idOfInventories = new ArrayList<>();

            //Get all the id(s) of the inventories related to the product
            inventoryListForProduct.stream().forEach(inventoryViewModel -> idOfInventories.add(inventoryViewModel.getInventoryId()));

            if(emptyInventoryForProduct.size() == inventoryListForProduct.size()){
                int count = 0;

                for (int inventoryId : idOfInventories) {
                    try {
                        invoiceService.getInvoiceItemsByInventoryId(inventoryId);
                    } catch (RuntimeException g) {
                        count++;
                    }
                }

                if(count != inventoryListForProduct.size()){
                    throw new DeleteNotAllowedException("Impossible Deletion, there is InvoiceItems related to the Product");
                }else{
                    productService.deleteProduct(id);
                }

            }else{
                throw new DeleteNotAllowedException("Impossible Deletion, there is Products still in inventory");
            }

        }catch (RuntimeException e){
            productService.deleteProduct(id);
        }

    }

}
