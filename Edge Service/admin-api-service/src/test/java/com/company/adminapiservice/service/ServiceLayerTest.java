package com.company.adminapiservice.service;

import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.InventoryViewModel;
import com.company.adminapiservice.viewmodel.InvoiceItem;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

        setUpCustomerServiceMock();
        setUpInventoryServiceMock();
        setUpInvoiceServiceMock();
        setUpLevelUpServiceMock();
        setUpProductServiceMock();

        serviceLayer = new ServiceLayer(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpCustomerServiceMock(){
        customerService = mock(CustomerService.class);

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

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(790);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(970);
        invoiceItem2.setInvoiceId(2);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(3);
        invoiceItem2.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceItemId(791);
        invoiceItem3.setInvoiceId(3);
        invoiceItem3.setInventoryId(2);
        invoiceItem3.setQuantity(3);
        invoiceItem3.setUnitPrice(new BigDecimal("4.99"));

        //For inventoryId 3
        InvoiceItem invoiceItem4 = new InvoiceItem();
        invoiceItem4.setInvoiceItemId(791);
        invoiceItem4.setInvoiceId(3);
        invoiceItem4.setInventoryId(3);
        invoiceItem4.setQuantity(3);
        invoiceItem4.setUnitPrice(new BigDecimal("4.99"));

        //List with all the invoiceItems for inventoryId
        List<InvoiceItem> itemsForInventory1 = new ArrayList<>();
        itemsForInventory1.add(invoiceItem);
        itemsForInventory1.add(invoiceItem2);

        List<InvoiceItem> itemsForInventory2 = new ArrayList<>();
        itemsForInventory2.add(invoiceItem3);

        List<InvoiceItem> itemsForInventory3 = new ArrayList<>();
        itemsForInventory3.add(invoiceItem4);

        //For this case the inventories have invoices related
        doReturn(itemsForInventory1).when(invoiceService).getInvoiceItemsByInventoryId(1);
        doReturn(itemsForInventory2).when(invoiceService).getInvoiceItemsByInventoryId(2);
        doReturn(itemsForInventory3).when(invoiceService).getInvoiceItemsByInventoryId(3);

        //For the product104
        doThrow(new RuntimeException()).when(invoiceService).getInvoiceItemsByInventoryId(4);


    }

    public void setUpLevelUpServiceMock(){
        levelUpService = mock(LevelUpService.class);
    }

    public void setUpProductServiceMock(){
        productService = mock(ProductService.class);
    }

    @Test
    public void deleteProductTest() {

        //serviceLayer.deleteProduct(100);

        //serviceLayer.deleteProduct(103);

        //serviceLayer.deleteProduct(104);

        serviceLayer.deleteProduct(105);

    }
}