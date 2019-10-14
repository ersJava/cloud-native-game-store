package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.DeleteNotAllowedException;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.InventoryViewModel;
import com.company.adminapiservice.viewmodel.InvoiceItemViewModel;
import com.company.adminapiservice.viewmodel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerAdminInventoryTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayerAdmin serviceLayerAdmin;

    @Before
    public void setUp() throws Exception {

        setUpInventoryServiceMock();

        setUpProductServiceMock();

        setUpInvoiceServiceMock();

        serviceLayerAdmin = new ServiceLayerAdmin(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }
    public void setUpInvoiceServiceMock(){
        invoiceService = mock(InvoiceService.class);

        List<InvoiceItemViewModel> canBeDeleted = new ArrayList<>();

        doReturn(canBeDeleted).when(invoiceService).getInvoiceItemsByInventoryId(7);

        //For An Inventory that can not be deleted
        InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();
        invoiceItem.setInvoiceItemId(790);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));

        List<InvoiceItemViewModel> forInventory14 = new ArrayList<>();
        forInventory14.add(invoiceItem);

        doReturn(forInventory14).when(invoiceService).getInvoiceItemsByInventoryId(14);
    }

    public void setUpProductServiceMock(){
        productService = mock(ProductService.class);

        ProductViewModel product = new ProductViewModel();

        product.setProductId(1);
        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice("250.36");
        product.setUnitCost("200.11");

        doReturn(product).when(productService).getProduct(100);
    }

    public void setUpInventoryServiceMock(){
        inventoryService = mock(InventoryService.class);

        InventoryViewModel inventory = new InventoryViewModel();

        inventory.setInventoryId(1);
        inventory.setProductId(100);
        inventory.setQuantity(7);

        //Calling Object
        InventoryViewModel inventoryC = new InventoryViewModel();
        inventoryC.setProductId(100);
        inventoryC.setQuantity(7);

        doReturn(inventory).when(inventoryService).createInventory(inventoryC);
        doReturn(inventory).when(inventoryService).getInventory(1);

        //Mock for getAllInventory
        InventoryViewModel inventory2 = new InventoryViewModel();

        inventory2.setInventoryId(2);
        inventory2.setProductId(101);
        inventory2.setQuantity(14);

        List<InventoryViewModel> listInventories = new ArrayList<>();
        listInventories.add(inventory);
        listInventories.add(inventory2);

        doReturn(listInventories).when(inventoryService).getAllInventories();

        //Mock for getAllInventoriesByProductId
        List<InventoryViewModel> listInventoriesByProduct = new ArrayList<>();
        listInventoriesByProduct.add(inventory);

        doReturn(listInventoriesByProduct).when(inventoryService).getAllInventoriesByProductId(100);

        //Mock for update
        InventoryViewModel inventoryForUpdate = new InventoryViewModel();

        inventoryForUpdate.setInventoryId(5);
        inventoryForUpdate.setProductId(100);
        inventoryForUpdate.setQuantity(7);

        doNothing().when(inventoryService).updateInventory(inventoryForUpdate.getInventoryId(), inventoryForUpdate);
        doReturn(inventoryForUpdate).when(inventoryService).getInventory(5);

        //Mock for delete
        doNothing().when(inventoryService).deleteInventory(7);
        doReturn(null).when(inventoryService).getInventory(7);
    }

    @Test
    public void createGetInventory() {
        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setProductId(100);
        inventory.setQuantity(7);

        //Create an Inventory
        inventory = serviceLayerAdmin.createInventory(inventory);

        //Reading the added Inventory from the service
        InventoryViewModel fromService = serviceLayerAdmin.getInventory(inventory.getInventoryId());

        //Making sure that they are equal
        assertEquals(inventory, fromService);
    }

    @Test
    public void getAllInventories() {
        List<InventoryViewModel> allInventories = serviceLayerAdmin.getAllInventories();

        assertEquals(allInventories.size(), 2);
    }

    @Test
    public void getAllInventoriesByProductId() {
        List<InventoryViewModel> inventoriesByProductId = serviceLayerAdmin.getAllInventoriesByProductId(100);

        assertEquals(inventoriesByProductId.size(),1);
    }

    @Test
    public void updateInventory() {
        InventoryViewModel inventoryForUpdate = new InventoryViewModel();

        inventoryForUpdate.setInventoryId(5);
        inventoryForUpdate.setProductId(100);
        inventoryForUpdate.setQuantity(7);

        serviceLayerAdmin.updateInventory(inventoryForUpdate.getInventoryId(), inventoryForUpdate);

        InventoryViewModel fromService = serviceLayerAdmin.getInventory(inventoryForUpdate.getInventoryId());

        assertEquals(inventoryForUpdate, fromService);
    }

    @Test
    public void deleteInventory() {

        //For an Inventory without InvoiceItems
        serviceLayerAdmin.deleteInventory(7);

        InventoryViewModel fromService = serviceLayerAdmin.getInventory(7);

        assertNull(fromService);

        String msg = "";
        //For an Inventory with InvoiceItems
        try{
            serviceLayerAdmin.deleteInventory(14);
        }catch (DeleteNotAllowedException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, this Inventory register have associated InvoiceItems",msg);
    }
}