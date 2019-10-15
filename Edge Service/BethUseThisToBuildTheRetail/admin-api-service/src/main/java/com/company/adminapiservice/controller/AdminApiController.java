package com.company.adminapiservice.controller;

import com.company.adminapiservice.exception.UpdateNotAllowedException;
import com.company.adminapiservice.service.ServiceLayer;
import com.company.adminapiservice.service.ServiceLayerAdmin;
import com.company.adminapiservice.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class AdminApiController {

    @Autowired
    ServiceLayer serviceLayer;

    @Autowired
    ServiceLayerAdmin serviceLayerAdmin;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////CUSTOMER ENDPOINTS/////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customerFrontEnd
    //Create a Customer receiving a FrontEndCustomerViewModel
    @RequestMapping(value = "/customerFrontEnd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FrontEndCustomerViewModel createCostumerFrontEnd(@RequestBody @Valid FrontEndCustomerViewModel fecvm) {

        return serviceLayerAdmin.createCustomer(fecvm);
    }

    //Reading a FrontEndCustomerViewModel
    @RequestMapping(value = "/customerFrontEnd/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public FrontEndCustomerViewModel getCostumerFrontEnd(@PathVariable int id) {

        return serviceLayerAdmin.getCustomerFrontEnd(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customer
    //Create a Customer receiving a CustomerViewModel
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel createCostumer(@RequestBody @Valid CustomerViewModel cvm) {

        return serviceLayerAdmin.createCustomer(cvm);
    }

    //Get aall CustomerViewModel
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCostumer() {

        return serviceLayerAdmin.getAllCustomers();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customer{id}
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCostumer(@PathVariable int id) {

        return serviceLayerAdmin.getCustomer(id);
    }

    //Update CustomerViewModel
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCostumer(@PathVariable int id, @RequestBody @Valid CustomerViewModel cvm) {

        if(id != cvm.getCustomerId()){
            throw new UpdateNotAllowedException("Update not allowed, the id in the PathVariable does not match with the id in the RequestBody");
        }else{
            serviceLayerAdmin.updateCustomer(cvm);
        }
    }

    //Delete CustomerViewModel
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCostumer(@PathVariable int id) {

        serviceLayerAdmin.deleteCustomer(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////INVENTORY ENDPOINTS////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /inventory
    //Create a Customer receiving a CustomerViewModel
    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel ivm){
        return serviceLayerAdmin.createInventory(ivm);
    }

    //Get all the inventory registers
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getAllInventories(){
        return serviceLayerAdmin.getAllInventories();
    }

    //uri: /inventory/{id}//////////////////////////////////////////////////////////////////////////////////////////////
    //Get Inventory for the inventoryId
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    InventoryViewModel getInventory(@PathVariable int id){
        return serviceLayerAdmin.getInventory(id);
    }

    //Update an Inventory Registry
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateInventory(@PathVariable("id") Integer id, @RequestBody @Valid InventoryViewModel ivm){
        if(id != ivm.getInventoryId()){
            throw new UpdateNotAllowedException("Update not allowed, the id in the PathVariable does not match with the id in the RequestBody");
        }else{
            serviceLayerAdmin.updateInventory(id,ivm);
        }
    }

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteInventory(@PathVariable int id){
        serviceLayerAdmin.deleteInventory(id);
    }

    //uri: /inventory/product/{id} /////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/inventory/product/{productId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getAllInventoriesByProductId(@PathVariable int productId){
        return serviceLayerAdmin.getAllInventoriesByProductId(productId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////INVOICE ENDPOINTS////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /invoices////////////////////////////////////////////////////////////////////////////////////////////////////
    //Create invoice registry
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    OrderViewModel createInvoice(@RequestBody @Valid OrderViewModel ovm){

        return serviceLayer.processOrder(ovm);
    }

    //Get all invoices
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InvoiceViewModel> getAllInvoices(){
        return serviceLayer.getAllInvoices();
    }

    //uri: /invoices/{id}///////////////////////////////////////////////////////////////////////////////////////////////
    //Get an Invoice
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable int id){
        return serviceLayer.getInvoice(id);
    }

    //uri: /invoices/customer/{customerId}//////////////////////////////////////////////////////////////////////////////
    //Get Invoice(s) by customerId
    @RequestMapping(value = "/invoices/customer/{customerId}", method = RequestMethod.GET)
    List<InvoiceViewModel> getInvoicesByCustomerId (@PathVariable int customerId){
        return serviceLayer.getInvoicesByCustomerId(customerId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////LEVELUP ENDPOINTS//////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /levelup
    //Create levelup account
    @RequestMapping(value = "/levelup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    LevelUpViewModel createLevelUpAccount(@RequestBody @Valid LevelUpViewModel lvm){
        return serviceLayerAdmin.createLevelUp(lvm);
    }

    //Get all the accounts
    @RequestMapping(value = "/levelup", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<LevelUpViewModel> getAllLevelUpAccounts(){
        return serviceLayerAdmin.getAllLevelUpAccounts();
    }

    //uri: /levelup/{id}////////////////////////////////////////////////////////////////////////////////////////////////
    //Get account for the specified Id
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getLevelUpAccount(@PathVariable int id){
        return serviceLayerAdmin.getLevelUpAccount(id);
    }

    //Delete account for the specified Id
    @RequestMapping(value = "/levelup/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteLevelUpAccount(@PathVariable int id){
        serviceLayerAdmin.deleteLevelUpAccount(id);
    }

    //uri: /levelup/points/{customerId}////////////////////////////////////////////////////////////////////////////////////////////////
    //Update points by customerId
    @RequestMapping(value = "/levelup/points/{customerId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updatePointsOnAccount(@PathVariable int customerId, @RequestBody @Valid LevelUpViewModel lvm){

        if(customerId != lvm.getCustomerId()){
            throw new UpdateNotAllowedException("Update not allowed, the id in the PathVariable does not match with the id in the RequestBody");
        }else{
            serviceLayerAdmin.updatePointsInAccount(customerId,lvm);
        }
    }

    //uri: /levelup/customer/{customerId}///////////////////////////////////////////////////////////////////////////////
    //Get account by CustomerId
    @RequestMapping(value = "/levelup/customer/{customerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getLevelUpAccountByCustomerId(@PathVariable("customerId") int customerId){
        return serviceLayerAdmin.getLevelUpAccountByCustomerId(customerId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////PRODUCT ENDPOINTS////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uri: /product
    //Create a new Product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    ProductViewModel newProduct(@RequestBody @Valid ProductViewModel pvm){
        return serviceLayerAdmin.createProduct(pvm);
    }

    //Get all products
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<ProductViewModel> getAllProducts(){
        return serviceLayerAdmin.getAllProducts();
    }


    //uri: /product/{id}////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    ProductViewModel getProduct(@PathVariable int id){
        return serviceLayerAdmin.getProduct(id);
    }

    //Update a existing Product
    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateProduct(@PathVariable int id, @RequestBody @Valid ProductViewModel pvm){

        if (id != pvm.getProductId()){
            throw new UpdateNotAllowedException("Update not allowed, the id in the PathVariable does not match with the id in the RequestBody");
        }else{
            serviceLayerAdmin.updateProduct(pvm);
        }
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteProduct(@PathVariable int id){
        serviceLayerAdmin.deleteProduct(id);
    }
}
