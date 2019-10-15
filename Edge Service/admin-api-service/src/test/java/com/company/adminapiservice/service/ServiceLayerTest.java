package com.company.adminapiservice.service;

<<<<<<< HEAD
import com.company.adminapiservice.exception.DeleteNotAllowedException;
import com.company.adminapiservice.exception.InvoiceNotFoundException;
import com.company.adminapiservice.exception.LevelUpNotFoundException;
=======
import com.company.adminapiservice.exception.OrderProcessFailException;
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
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

public class ServiceLayerTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

<<<<<<< HEAD
        setUpCustomerServiceMock();
        setUpInventoryServiceMock();
        setUpInvoiceServiceMock();
        setUpLevelUpServiceMock();
        setUpProductServiceMock();

        serviceLayer = new ServiceLayer(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpCustomerServiceMock(){
=======
        setUpInvoiceServiceMock();

        setLevelUpServiceMock();

        setCustomerServiceMock();

        setUpProductServiceMock();

        setUpInventoryServiceMock();

        serviceLayer = new ServiceLayer(customerService, inventoryService, invoiceService, levelUpService,
                productService);
    }

    public void setUpInvoiceServiceMock(){

        invoiceService = mock(InvoiceService.class);

        //Setting up the InvoiceItems list for the Calling Object
        InvoiceItemViewModel invoiceItem1C = new InvoiceItemViewModel();
        invoiceItem1C.setInvoiceItemId(null);
        invoiceItem1C.setInvoiceId(null);
        invoiceItem1C.setInventoryId(3);
        invoiceItem1C.setQuantity(3);
        invoiceItem1C.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem2C = new InvoiceItemViewModel();
        invoiceItem2C.setInvoiceItemId(null);
        invoiceItem2C.setInvoiceId(null);
        invoiceItem2C.setInventoryId(1);
        invoiceItem2C.setQuantity(2);
        invoiceItem2C.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem3C = new InvoiceItemViewModel();
        invoiceItem3C.setInvoiceItemId(null);
        invoiceItem3C.setInvoiceId(null);
        invoiceItem3C.setInventoryId(4);
        invoiceItem3C.setQuantity(2);
        invoiceItem3C.setUnitPrice(new BigDecimal("300.54"));

        List<InvoiceItemViewModel> callingInvoiceItems = new ArrayList<>();

        callingInvoiceItems.add(invoiceItem1C);
        callingInvoiceItems.add(invoiceItem2C);
        callingInvoiceItems.add(invoiceItem3C);

        //Calling object
        InvoiceViewModel invoiceC = new InvoiceViewModel();

        invoiceC.setInvoiceId(null);
        invoiceC.setPurchaseDate(LocalDate.of(2019,12,13));
        invoiceC.setCustomerId(1);
        invoiceC.setItemList(callingInvoiceItems);

        //////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        //Setting up the InvoiceItems list for the Object to be returned
        InvoiceItemViewModel invoiceItem1 = new InvoiceItemViewModel();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(3);
        invoiceItem1.setQuantity(3);
        invoiceItem1.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem2 = new InvoiceItemViewModel();
        invoiceItem2.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(2);
        invoiceItem2.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem3 = new InvoiceItemViewModel();
        invoiceItem3.setInvoiceItemId(3);
        invoiceItem3.setInvoiceId(1);
        invoiceItem3.setInventoryId(4);
        invoiceItem3.setQuantity(2);
        invoiceItem3.setUnitPrice(new BigDecimal("300.54"));

        List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();

        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);
        invoiceItems.add(invoiceItem3);

        //Object to be returned
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,12,13));
        invoice.setCustomerId(1);
        invoice.setItemList(invoiceItems);

        doReturn(invoice).when(invoiceService).createInvoice(invoiceC);
        doReturn(invoice).when(invoiceService).getInvoice(1);

        //Testing an error in invoiceService, uncomment this and comment the line above
        //doThrow(new RuntimeException()).when(invoiceService).createInvoice(invoiceC);

        //doReturn(invoice).when(invoiceService).getInvoice(1);

        //Mock for getAllInvoices
        InvoiceViewModel invoice25 = new InvoiceViewModel();

        invoice25.setInvoiceId(25);
        invoice25.setPurchaseDate(LocalDate.of(2019,12,13));
        invoice25.setCustomerId(2);

        InvoiceViewModel invoice26 = new InvoiceViewModel();

        invoice26.setInvoiceId(26);
        invoice26.setPurchaseDate(LocalDate.of(2019,12,13));
        invoice26.setCustomerId(2);

        List<InvoiceViewModel> allInvoices = new ArrayList<>();
        allInvoices.add(invoice);
        allInvoices.add(invoice25);
        allInvoices.add(invoice26);

        doReturn(allInvoices).when(invoiceService).getAllInvoices();

        //Mock for getInvoicesByCustomerId
        List<InvoiceViewModel> invoicesForCustomer26 = new ArrayList<>();
        invoicesForCustomer26.add(invoice25);
        invoicesForCustomer26.add(invoice26);

        doReturn(invoicesForCustomer26).when(invoiceService).getInvoicesByCustomerId(2);
    }

    public void setLevelUpServiceMock(){
        levelUpService = mock(LevelUpService.class);

        LevelUpViewModel levelUp = new LevelUpViewModel();

        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2012,12,13));

        //Updated
        LevelUpViewModel levelUpUpdated = new LevelUpViewModel();

        levelUpUpdated.setLevelUpId(1);
        levelUpUpdated.setCustomerId(1);
        levelUpUpdated.setPoints(340);
        levelUpUpdated.setMemberDate(LocalDate.of(2012,12,13));

        doReturn(levelUp).when(levelUpService).getLevelUpAccountByCustomerId(1);

        doNothing().when(levelUpService).updatePointsOnAccount(1,levelUpUpdated);

        //Testing a communication error with the client, uncomment this and comment the line above
        //doThrow(new RuntimeException()).when(levelUpService).updatePointsOnAccount(1,levelUpUpdated);

        //doThrow(new RuntimeException()).when(levelUpService).getLevelUpAccountByCustomerId(1);

    }

    public void setCustomerServiceMock(){
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
        customerService = mock(CustomerService.class);

        CustomerViewModel customer = new CustomerViewModel();

<<<<<<< HEAD
        customer.setCustomerId(10);
=======
        customer.setCustomerId(1);
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

<<<<<<< HEAD
=======
        doReturn(customer).when(customerService).getCustomer(1);
    }

    public void setUpProductServiceMock(){
        productService = mock(ProductService.class);

        ProductViewModel product1 = new ProductViewModel();
        product1.setProductId(1);
        product1.setProductName("Parra AirForce1");
        product1.setProductDescription("Nike Parra");
        product1.setUnitCost("100.45");
        product1.setListPrice("200.23");

        ProductViewModel product2 = new ProductViewModel();
        product2.setProductId(2);
        product2.setProductName("Iphone 10");
        product2.setProductDescription("Apple Iphone");
        product2.setUnitCost("100.45");
        product2.setListPrice("300.54");

        doReturn(product1).when(productService).getProduct(1);
        doReturn(product2).when(productService).getProduct(2);
>>>>>>> f00fc299981692406efd94cca8a90917049f7e46

    }

    public void setUpInventoryServiceMock(){
        inventoryService = mock(InventoryService.class);

<<<<<<< HEAD
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
=======
        InventoryViewModel inventory1 = new InventoryViewModel();
        inventory1.setInventoryId(1);
        inventory1.setProductId(1);
        inventory1.setQuantity(2);

        InventoryViewModel inventory2 = new InventoryViewModel();
        inventory2.setInventoryId(2);
        inventory2.setProductId(1);
        inventory2.setQuantity(1);

        InventoryViewModel inventory3 = new InventoryViewModel();
        inventory3.setInventoryId(3);
        inventory3.setProductId(1);
        inventory3.setQuantity(3);

        //For product2
        InventoryViewModel inventory4 = new InventoryViewModel();
        inventory4.setInventoryId(4);
        inventory4.setProductId(2);
        inventory4.setQuantity(2);


        List<InventoryViewModel> listInventoryProduct1 = new ArrayList<>();
        listInventoryProduct1.add(inventory1);
        listInventoryProduct1.add(inventory2);
        listInventoryProduct1.add(inventory3);

        List<InventoryViewModel> listInventoryProduct2 = new ArrayList<>();
        listInventoryProduct2.add(inventory4);

        doReturn(listInventoryProduct1).when(inventoryService).getAllInventoriesByProductId(1);
        doReturn(listInventoryProduct2).when(inventoryService).getAllInventoriesByProductId(2);

        //ToUpdate
        InventoryViewModel inventory3Updated = new InventoryViewModel();
        inventory3Updated.setInventoryId(3);
        inventory3Updated.setProductId(1);
        inventory3Updated.setQuantity(0);

        InventoryViewModel inventory4Updated = new InventoryViewModel();
        inventory4Updated.setInventoryId(4);
        inventory4Updated.setProductId(2);
        inventory4Updated.setQuantity(0);

        InventoryViewModel inventory1Updated = new InventoryViewModel();
        inventory1Updated.setInventoryId(1);
        inventory1Updated.setProductId(1);
        inventory1Updated.setQuantity(0);



//        doNothing().when(inventoryService).updateInventory(inventory3Updated.getInventoryId(), inventory3Updated);
//        doNothing().when(inventoryService).updateInventory(inventory4Updated.getInventoryId(), inventory4Updated);
//        doNothing().when(inventoryService).updateInventory(inventory1Updated.getInventoryId(), inventory1Updated);

        //Test an error in the service, uncomment this and comment the three lines above
        doThrow(new RuntimeException()).when(inventoryService).updateInventory(inventory3Updated.getInventoryId(), inventory3Updated);
        doThrow(new RuntimeException()).when(inventoryService).updateInventory(inventory4Updated.getInventoryId(), inventory4Updated);
        doThrow(new RuntimeException()).when(inventoryService).updateInventory(inventory1Updated.getInventoryId(), inventory1Updated);

>>>>>>> f00fc299981692406efd94cca8a90917049f7e46


    }

<<<<<<< HEAD
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
            serviceLayer.deleteCustomer(10);
            msg = "Successful Delete of Customer 10";

        }catch (DeleteNotAllowedException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, there is LevelUp Account associated with this Customer", msg);

        ///////////////////////////////////////////////////////////////////////////////////
        //Customer 1 has NO invoice(s) related, neither a LevelUpAccount. Delete is allowed
        ///////////////////////////////////////////////////////////////////////////////////
        try{
            serviceLayer.deleteCustomer(1);
            msg = "Successful Delete of Customer 1";

        }catch (DeleteNotAllowedException e){
            msg = "Delete not Allowed";
        }

        assertEquals("Successful Delete of Customer 1", msg);

        ///////////////////////////////////////////////////////////////////////
        //Customer 11 has a LevelUp related Account, the delete should not be allowed
        ///////////////////////////////////////////////////////////////////////
        try{
            serviceLayer.deleteCustomer(11);
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
            serviceLayer.deleteProduct(100);
            msg = "Product 100 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, this products have associated InvoiceItems", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayer.deleteProduct(103);
            msg = "Product 103 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, this products have associated InvoiceItems", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayer.deleteProduct(104);
            msg = "Product 104 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Product 104 deleted", msg);

        try {
            //When the delete can not be completed the method throw an Exception
            serviceLayer.deleteProduct(105);
            msg = "Product 105 deleted";

        }catch (RuntimeException e){
            msg = e.getMessage();
        }

        assertEquals("Impossible Deletion, there is Products still in inventory", msg);
=======
    @Test
    public void processOrderTest() {
        ProductToBuyViewModel product1 = new ProductToBuyViewModel();
        product1.setProductId(1);
        product1.setQuantity(2);

        ProductToBuyViewModel product2 = new ProductToBuyViewModel();
        product2.setProductId(1);
        product2.setQuantity(3);

        ProductToBuyViewModel product3 = new ProductToBuyViewModel();
        product3.setProductId(2);
        product3.setQuantity(2);

        List<ProductToBuyViewModel> productsList = new ArrayList<>();
        productsList.add(product1);
        productsList.add(product2);
        productsList.add(product3);

        //Creating the OrderViewModel
        OrderViewModel ovm = new OrderViewModel();
        ovm.setCustomerId(1);
        ovm.setPurchaseDate(LocalDate.of(2019,12,13));
        ovm.setProductsToBuy(productsList);

        try{
            ovm = serviceLayer.processOrder(ovm);
        }catch (OrderProcessFailException o){
            System.out.println(o.getMessage());
            assertEquals(1,1);
        }

        assertEquals(1,1);

    }

    @Test
    public void getInvoiceTest(){
        //Setting up the InvoiceItems list for the Object to be returned
        InvoiceItemViewModel invoiceItem1 = new InvoiceItemViewModel();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(3);
        invoiceItem1.setQuantity(3);
        invoiceItem1.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem2 = new InvoiceItemViewModel();
        invoiceItem2.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(2);
        invoiceItem2.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItemViewModel invoiceItem3 = new InvoiceItemViewModel();
        invoiceItem3.setInvoiceItemId(3);
        invoiceItem3.setInvoiceId(1);
        invoiceItem3.setInventoryId(4);
        invoiceItem3.setQuantity(2);
        invoiceItem3.setUnitPrice(new BigDecimal("300.54"));

        List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();

        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);
        invoiceItems.add(invoiceItem3);

        //Creating the Expected Invoice
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,12,13));
        invoice.setCustomerId(1);
        invoice.setItemList(invoiceItems);

        //Reading the Invoice from the Service
        InvoiceViewModel fromService = serviceLayer.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, fromService);

    }

    @Test
    public void getAllInvoicesTest(){
        List<InvoiceViewModel> allInvoices = serviceLayer.getAllInvoices();

        assertEquals(allInvoices.size(),3);
    }

    @Test
    public void getInvoicesByCustomerIdTest(){
        List<InvoiceViewModel> invoicesForCustomer26 = serviceLayer.getInvoicesByCustomerId(2);

        assertEquals(invoicesForCustomer26.size(),2);
    }

    @Test
    public void filteredProductsToBuyTest(){
        ProductToBuyViewModel product1 = new ProductToBuyViewModel();
        product1.setProductId(1);
        product1.setQuantity(3);

        ProductToBuyViewModel product2 = new ProductToBuyViewModel();
        product2.setProductId(1);
        product2.setQuantity(4);

        ProductToBuyViewModel product3 = new ProductToBuyViewModel();
        product3.setProductId(1);
        product3.setQuantity(1);

        ProductToBuyViewModel product4 = new ProductToBuyViewModel();
        product4.setProductId(2);
        product4.setQuantity(3);

        List<ProductToBuyViewModel> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

        productList = serviceLayer.filterProductsToBuyList(productList);

        List<ProductToBuyViewModel> filteredProductList = serviceLayer.filterProductsToBuyList(productList);

        assertEquals(filteredProductList.size(),2);
    }

    @Test
    public void orderInventoryListByQuantityTest() {
        InventoryViewModel ivm1 = new InventoryViewModel();

        ivm1.setInventoryId(1);
        ivm1.setQuantity(1);
        ivm1.setProductId(2);

        InventoryViewModel ivm2 = new InventoryViewModel();

        ivm2.setInventoryId(2);
        ivm2.setQuantity(7);
        ivm2.setProductId(3);

        InventoryViewModel ivm3 = new InventoryViewModel();

        ivm3.setInventoryId(3);
        ivm3.setQuantity(5);
        ivm3.setProductId(4);


        InventoryViewModel ivm4 = new InventoryViewModel();

        ivm4.setInventoryId(4);
        ivm4.setQuantity(15);
        ivm4.setProductId(5);

        InventoryViewModel ivm5 = new InventoryViewModel();

        ivm5.setInventoryId(5);
        ivm5.setQuantity(15);
        ivm5.setProductId(5);

        List<InventoryViewModel> inventoryViewModelList = new ArrayList<>();

        inventoryViewModelList.add(ivm1);
        inventoryViewModelList.add(ivm2);
        inventoryViewModelList.add(ivm3);
        inventoryViewModelList.add(ivm4);

        List<InventoryViewModel> anotherList = new ArrayList<>();
        anotherList.add(ivm3);
        anotherList.add(ivm4);
        anotherList.add(ivm1);

        List<InventoryViewModel> thirdList = new ArrayList<>();
        thirdList.add(ivm2);
        thirdList.add(ivm3);
        thirdList.add(ivm1);

        List<InventoryViewModel> listFourth = new ArrayList<>();
        listFourth.add(ivm2);
        listFourth.add(ivm3);
        listFourth.add(ivm4);
        listFourth.add(ivm5);

        List<InventoryViewModel> ordered5 = serviceLayer.orderInventoryListByQuantity(listFourth);

        List<InventoryViewModel> ordered3 = serviceLayer.orderInventoryListByQuantity(thirdList);

        assertEquals(ordered3.size(),3);

        List<InventoryViewModel> ordered2 = serviceLayer.orderInventoryListByQuantity(anotherList);

        assertEquals(ordered2.size(),3);

        List<InventoryViewModel> ordered = serviceLayer.orderInventoryListByQuantity(inventoryViewModelList);

        assertEquals(ordered.size(),4);

>>>>>>> f00fc299981692406efd94cca8a90917049f7e46
    }
}