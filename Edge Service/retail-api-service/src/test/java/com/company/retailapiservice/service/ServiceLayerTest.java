package com.company.retailapiservice.service;

import com.company.retailapiservice.util.feign.*;
import com.company.retailapiservice.viewmodel.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Component
public class ServiceLayerTest {

    private ServiceLayer serviceLayer;

    private CustomerClient customerClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;

    @Before
    public void setUp() throws Exception {

        setUpCustomerClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
        setUpProductClientMock();

        serviceLayer = new ServiceLayer(customerClient, inventoryClient, invoiceClient, levelUpClient, productClient);

    }

    private void setUpCustomerClientMock() {

        customerClient = mock(CustomerClient.class);

        CustomerViewModel customer = new CustomerViewModel();
        customer.setCustomerId(1);
        customer.setFirstName("Milo");
        customer.setLastName("Dufresne");
        customer.setStreet("Biscuit Street");
        customer.setCity("Barksville");
        customer.setZip("33303");
        customer.setEmail("goldenboy1@woof.com");
        customer.setPhone("777-5757");

        // Mock getCustomer by Id
        doReturn(customer).when(customerClient).getCustomer(1);

    }

    private void setUpInventoryClientMock() {

        inventoryClient = mock(InventoryClient.class);

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(40001);
        inventory.setProductId(7812);
        inventory.setQuantity(25);

        // Mock getInventory by Id
        doReturn(inventory).when(inventoryClient).getInventory(40001);
        doThrow(new RuntimeException()).when(inventoryClient).getInventory(4);

        // Update mock data
        InventoryViewModel inventoryUpdate = new InventoryViewModel();
        inventoryUpdate.setInventoryId(100);
        inventoryUpdate.setProductId(7812);
        inventoryUpdate.setQuantity(20);

        // Mock updateInventory (quantity)
        doNothing().when(inventoryClient).updateInventory(100, inventoryUpdate);
        doReturn(inventoryUpdate).when(inventoryClient).getInventory(100);
    }

    private void setUpInvoiceClientMock() {

        invoiceClient = mock(InvoiceClient.class);

        // Completed Obj with Id
        InvoiceItemViewModel invoiceItem = new InvoiceItemViewModel();
        invoiceItem.setInvoiceItemId(790);
        invoiceItem.setInvoiceId(11);
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("22.99"));

        List<InvoiceItemViewModel> listWithId = new ArrayList<>();
        listWithId.add(invoiceItem);

        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(11);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 12 ));
        invoice.setItemList(listWithId);

        // Calling obj
        InvoiceItemViewModel invoiceItem1 = new InvoiceItemViewModel();
        invoiceItem1.setInventoryId(40001);
        invoiceItem1.setQuantity(3);
        invoiceItem1.setUnitPrice(new BigDecimal("22.99")); // update rest mocks

        List<InvoiceItemViewModel> listWithoutId = new ArrayList<>();
        listWithoutId.add(invoiceItem1);

        InvoiceViewModel invoice1 = new InvoiceViewModel();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 10, 12 ));
        invoice1.setItemList(listWithoutId);

        // Mock createInvoice
        doReturn(invoice).when(invoiceClient).createInvoice(invoice1);

        // Mock getInvoice By id
        doReturn(invoice).when(invoiceClient).getInvoice(11);

    }

    private void setUpLevelUpClientMock() {

        levelUpClient = mock(LevelUpClient.class);

        LevelUpViewModel levelUp = new LevelUpViewModel();
        levelUp.setLevelUpId(500);
        levelUp.setCustomerId(1);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2017, 1, 1 ));

        // Mock getLevelUpAccountByCustomerId
        doReturn(levelUp).when(levelUpClient).getLevelUpAccountByCustomerId(1);

        // Mock update
        LevelUpViewModel levelUpUpdate = new LevelUpViewModel();
        levelUpUpdate.setLevelUpId(500);
        levelUpUpdate.setCustomerId(1);
        levelUpUpdate.setPoints(300);
        levelUpUpdate.setMemberDate(LocalDate.of(2017, 1, 1 ));

        // Mock updatePoints & getLevelUp by Id
        doNothing().when(levelUpClient).updatePointsOnAccount(1, levelUpUpdate);
        doReturn(levelUpUpdate).when(levelUpClient).getLevelUpAccount(500);

    }

    private void setUpProductClientMock() {

        productClient = mock(ProductClient.class);

        ProductViewModel product = new ProductViewModel();
        product.setProductId(7812);
        product.setProductName("Snuggle Puppy");
        product.setProductDescription("Plush puppy toy recreates the intimacy and physical warmth and a “real-feel” heartbeat. The “real-feel” pulsing heartbeat comes with 2 AAA batteries.");
        product.setListPrice("22.99"); // price that goes makes the invoice
        product.setUnitCost("10.99");

        ProductViewModel product1 = new ProductViewModel();
        product1.setProductName("Snuggle Puppy");
        product1.setProductDescription("Plush puppy toy recreates the intimacy and physical warmth and a “real-feel” heartbeat. The “real-feel” pulsing heartbeat comes with 2 AAA batteries.");
        product1.setListPrice("22.99"); // price that goes makes the invoice
        product1.setUnitCost("10.99");

        // Mock getProductById
        doReturn(product).when(productClient).getProduct(7812);
    }

    // - - - - - Product Test - - - - - -

    // - - - - - Inventory Test - - - - - -

    // - - - - - Invoice Test - - - - - -
    @Test
    public void createFindInvoiceTest() {

        CustomerViewModel customer = new CustomerViewModel();
        customer.setCustomerId(1);
        customer.setFirstName("Milo");
        customer.setLastName("Dufresne");
        customer.setStreet("Biscuit Street");
        customer.setCity("Barksville");
        customer.setZip("33303");
        customer.setEmail("goldenboy1@woof.com");
        customer.setPhone("777-5757");

        ProductViewModel product = new ProductViewModel();
        product.setProductId(7812);
        product.setProductName("Snuggle Puppy");
        product.setProductDescription("Plush puppy toy recreates the intimacy and physical warmth and a “real-feel” heartbeat. The “real-feel” pulsing heartbeat comes with 2 AAA batteries.");
        product.setListPrice("22.99"); // price that goes makes the invoice
        product.setUnitCost("10.99");

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(40001);
        inventory.setProductId(7812);
        inventory.setQuantity(25);

        // Obj to go to Add Method
        InvoiceItemViewModel invoiceItem1 = new InvoiceItemViewModel();
        invoiceItem1.setInventoryId(40001);
        invoiceItem1.setQuantity(3);

        List<InvoiceItemViewModel> listWithoutId = new ArrayList<>();
        listWithoutId.add(invoiceItem1);

        InvoiceViewModel invoice1 = new InvoiceViewModel();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 10, 12 ));
        invoice1.setItemList(listWithoutId);

       invoice1 = serviceLayer.addInvoice(invoice1);

       InvoiceViewModel fromService = serviceLayer.findInvoice(invoice1.getInvoiceId());

       assertEquals(invoice1, fromService);


    }

    // - - - - - Level Up Test - - - - - -
    @Test
    public void updateLevelUpPtsTest() {

        LevelUpViewModel lvmUpdate = new LevelUpViewModel();
        lvmUpdate.setLevelUpId(500);
        lvmUpdate.setCustomerId(1);
        lvmUpdate.setPoints(300);
        lvmUpdate.setMemberDate(LocalDate.of(2017, 1, 1 ));

        levelUpClient.updatePointsOnAccount(1, lvmUpdate);

        LevelUpViewModel fromClient = levelUpClient.getLevelUpAccount(lvmUpdate.getLevelUpId());

        assertEquals(fromClient, lvmUpdate);

    }

    @Test
    public void getLevelUpByCustomerIdTest() {

        LevelUpViewModel levelUp = new LevelUpViewModel();
        levelUp.setLevelUpId(500);
        levelUp.setCustomerId(1);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2017, 1, 1 ));

        LevelUpViewModel fromClient = levelUpClient.getLevelUpAccountByCustomerId(levelUp.getCustomerId());
        assertEquals(fromClient, levelUp);

    }

    // - - - - - Product Test - - - - - -
    @Test
    public void getProductTest() {

        ProductViewModel product = new ProductViewModel();
        product.setProductId(7812);
        product.setProductName("Snuggle Puppy");
        product.setProductDescription("Plush puppy toy recreates the intimacy and physical warmth and a “real-feel” heartbeat. The “real-feel” pulsing heartbeat comes with 2 AAA batteries.");
        product.setListPrice("22.99"); // price that goes makes the invoice
        product.setUnitCost("10.99");

        ProductViewModel fromClient = productClient.getProduct(7812);

        assertEquals(product, fromClient);

    }




}
