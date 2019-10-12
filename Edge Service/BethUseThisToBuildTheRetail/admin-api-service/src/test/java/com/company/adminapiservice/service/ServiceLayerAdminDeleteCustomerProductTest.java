package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.DeleteNotAllowedException;
import com.company.adminapiservice.exception.InvoiceNotFoundException;
import com.company.adminapiservice.exception.LevelUpNotFoundException;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ServiceLayerAdminDeleteCustomerProductTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayerAdmin serviceLayerAdmin;

    @Before
    public void setUp() throws Exception {

        setUpCustomerServiceMock();
        setUpInventoryServiceMock();
        setUpInvoiceServiceMock();
        setUpLevelUpServiceMock();
        setUpProductServiceMock();

        serviceLayerAdmin = new ServiceLayerAdmin(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpCustomerServiceMock(){
        customerService = mock(CustomerService.class);

        CustomerViewModel customer = new CustomerViewModel();

        customer.setCustomerId(10);
        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");
    }

    public void setUpInventoryServiceMock(){
        inventoryService = mock(InventoryService.class);

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(1);
        inventory.setProductId(100);
        //The quantity should be 0 in order to delete
        inventory.setQuantity(0);

        InventoryViewModel inventory2 = new InventoryViewModel();
        inventory2.setInventoryId(2);
        inventory2.setProductId(100);
        //The quantity should be 0 in order to delete
        inventory2.setQuantity(0);

        InventoryViewModel inventory3 = new InventoryViewModel();
        inventory3.setInventoryId(3);
        inventory3.setProductId(103);
        //The quantity should be 0 in order to delete
        inventory3.setQuantity(0);

        InventoryViewModel inventory4 = new InventoryViewModel();
        inventory4.setInventoryId(4);
        inventory4.setProductId(104);
        //The quantity should be 0 in order to delete
        inventory4.setQuantity(0);

        //Inventory with existing elements for the product
        InventoryViewModel inventory5 = new InventoryViewModel();
        inventory5.setInventoryId(4);
        inventory5.setProductId(105);
        //The quantity should be 0 in order to delete
        inventory5.setQuantity(7);

        List<InventoryViewModel> listForProduct100 = new ArrayList<>();
        listForProduct100.add(inventory);
        listForProduct100.add(inventory2);

        List<InventoryViewModel> listForProduct103 = new ArrayList<>();
        listForProduct103.add(inventory3);

        List<InventoryViewModel> listForProduct104 = new ArrayList<>();
        listForProduct104.add(inventory4);

        List<InventoryViewModel> listForProduct105 = new ArrayList<>();
        listForProduct105.add(inventory5);


        doReturn(listForProduct100).when(inventoryService).getAllInventoriesByProductId(100);
        doReturn(listForProduct103).when(inventoryService).getAllInventoriesByProductId(103);
        doReturn(listForProduct104).when(inventoryService).getAllInventoriesByProductId(104);
        doReturn(listForProduct105).when(inventoryService).getAllInventoriesByProductId(105);
    }

    public void setUpInvoiceServiceMock(){
        invoiceService = mock(InvoiceService.class);
        ////////////////////////////////////////////////////////////////
        //ForDelete
        ////////////////////////////////////////////////////////////////

        InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();
        invoiceItem.setInvoiceItemId(790);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItemViewModel invoiceItem2 = new InvoiceItemViewModel();
        invoiceItem2.setInvoiceItemId(970);
        invoiceItem2.setInvoiceId(2);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(3);
        invoiceItem2.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItemViewModel invoiceItem3 = new InvoiceItemViewModel();
        invoiceItem3.setInvoiceItemId(791);
        invoiceItem3.setInvoiceId(3);
        invoiceItem3.setInventoryId(2);
        invoiceItem3.setQuantity(3);
        invoiceItem3.setUnitPrice(new BigDecimal("4.99"));

        //For inventoryId 3
        InvoiceItemViewModel invoiceItem4 = new InvoiceItemViewModel();
        invoiceItem4.setInvoiceItemId(791);
        invoiceItem4.setInvoiceId(3);
        invoiceItem4.setInventoryId(3);
        invoiceItem4.setQuantity(3);
        invoiceItem4.setUnitPrice(new BigDecimal("4.99"));

        //List with all the invoiceItems for inventoryId
        List<InvoiceItemViewModel> itemsForInventory1 = new ArrayList<>();
        itemsForInventory1.add(invoiceItem);
        itemsForInventory1.add(invoiceItem2);

        List<InvoiceItemViewModel> itemsForInventory2 = new ArrayList<>();
        itemsForInventory2.add(invoiceItem3);

        List<InvoiceItemViewModel> itemsForInventory3 = new ArrayList<>();
        itemsForInventory3.add(invoiceItem4);

        //For this case the inventories have invoices related
        doReturn(itemsForInventory1).when(invoiceService).getInvoiceItemsByInventoryId(1);
        doReturn(itemsForInventory2).when(invoiceService).getInvoiceItemsByInventoryId(2);
        doReturn(itemsForInventory3).when(invoiceService).getInvoiceItemsByInventoryId(3);

        //For the product104
        doThrow(new RuntimeException()).when(invoiceService).getInvoiceItemsByInventoryId(4);

        //For Testing the delete of a Customer
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));

        InvoiceViewModel invoice2 = new InvoiceViewModel();
        invoice2.setInvoiceId(1);
        invoice2.setCustomerId(10);
        invoice2.setPurchaseDate(LocalDate.of(2019, 10, 15));

        //Mock Methods
        List<InvoiceViewModel> invoicesForCustomer10 = new ArrayList<>();
        invoicesForCustomer10.add(invoice);
        invoicesForCustomer10.add(invoice2);

        doReturn(invoicesForCustomer10).when(invoiceService).getInvoicesByCustomerId(10);
        doThrow(new InvoiceNotFoundException("No Invoices found for Customer 1")).when(invoiceService).getInvoicesByCustomerId(1);
        doThrow(new InvoiceNotFoundException("No Invoices found for Customer 11")).when(invoiceService).getInvoicesByCustomerId(11);


    }

    public void setUpLevelUpServiceMock(){

        levelUpService = mock(LevelUpService.class);

        LevelUpViewModel levelUp = new LevelUpViewModel();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        //For the Delete of a Customer
        doReturn(levelUp).when(levelUpService).getLevelUpAccountByCustomerId(11);
        doThrow(new LevelUpNotFoundException("No LevelUp Account found for customer")).when(levelUpService).getLevelUpAccountByCustomerId(1);


    }

    public void setUpProductServiceMock(){
        productService = mock(ProductService.class);
    }

    @Test
    public void deleteCustomer(){
        String msg;
        ///////////////////////////////////////////////////////////////////////
        //Customer 10 has invoice(s) related, the delete should not be allowed
        ///////////////////////////////////////////////////////////////////////
        try{
            serviceLayerAdmin.deleteCustomer(10);
            msg = "Successful Delete of Customer 10";

        }catch (DeleteNotAllowedException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, there is LevelUp Account associated with this Customer", msg);

        ///////////////////////////////////////////////////////////////////////////////////
        //Customer 1 has NO invoice(s) related, neither a LevelUpAccount. Delete is allowed
        ///////////////////////////////////////////////////////////////////////////////////
        try{
            serviceLayerAdmin.deleteCustomer(1);
            msg = "Successful Delete of Customer 1";

        }catch (DeleteNotAllowedException e){
            msg = "Delete not Allowed";
        }

        assertEquals("Successful Delete of Customer 1", msg);

        ///////////////////////////////////////////////////////////////////////
        //Customer 11 has a LevelUp related Account, the delete should not be allowed
        ///////////////////////////////////////////////////////////////////////
        try{
            serviceLayerAdmin.deleteCustomer(11);
            msg = "Successful Delete of Customer 11";

        }catch (DeleteNotAllowedException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, there is LevelUp Account associated with this Customer", msg);
    }

    @Test
    public void deleteProductTest() {
        String msg;

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayerAdmin.deleteProduct(100);
            msg = "Product 100 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, this products have associated InvoiceItems", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayerAdmin.deleteProduct(103);
            msg = "Product 103 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, this products have associated InvoiceItems", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayerAdmin.deleteProduct(104);
            msg = "Product 104 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Product 104 deleted", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayerAdmin.deleteProduct(105);
            msg = "Product 105 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, there is Products still in inventory", msg);
    }
}