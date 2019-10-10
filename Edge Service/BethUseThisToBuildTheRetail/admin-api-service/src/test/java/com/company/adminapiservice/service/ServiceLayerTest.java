package com.company.adminapiservice.service;

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
    InventoyService inventoyService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

        setUpInvoiceServiceMock();

        setLevelUpServiceMock();

        setCustomerServiceMock();

        setUpProductServiceMock();

        setUpInventoryServiceMock();

        serviceLayer = new ServiceLayer(customerService, inventoyService, invoiceService, levelUpService,
                productService);
    }

    public void setUpInvoiceServiceMock(){

        invoiceService = mock(InvoiceService.class);

        //Setting up the InvoiceItems list for the Calling Object
        InvoiceItem invoiceItem1C = new InvoiceItem();
        invoiceItem1C.setInvoiceItemId(0);
        invoiceItem1C.setInvoiceId(0);
        invoiceItem1C.setInventoryId(3);
        invoiceItem1C.setQuantity(3);
        invoiceItem1C.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItem invoiceItem2C = new InvoiceItem();
        invoiceItem2C.setInvoiceItemId(0);
        invoiceItem2C.setInvoiceId(0);
        invoiceItem2C.setInventoryId(1);
        invoiceItem2C.setQuantity(2);
        invoiceItem2C.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItem invoiceItem3C = new InvoiceItem();
        invoiceItem3C.setInvoiceItemId(0);
        invoiceItem3C.setInvoiceId(0);
        invoiceItem3C.setInventoryId(4);
        invoiceItem3C.setQuantity(2);
        invoiceItem3C.setUnitPrice(new BigDecimal("300.54"));

        List<InvoiceItem> callingInvoiceItems = new ArrayList<>();

        callingInvoiceItems.add(invoiceItem1C);
        callingInvoiceItems.add(invoiceItem2C);
        callingInvoiceItems.add(invoiceItem3C);

        //Calling object
        InvoiceViewModel invoiceC = new InvoiceViewModel();

        invoiceC.setPurchaseDate(LocalDate.of(2019,12,13));
        invoiceC.setCustomerId(1);
        invoiceC.setInvoiceItems(callingInvoiceItems);

        //////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        //Setting up the InvoiceItems list for the Object to be returned
        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceItemId(1);
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(3);
        invoiceItem1.setQuantity(3);
        invoiceItem1.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(2);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1);
        invoiceItem2.setQuantity(2);
        invoiceItem2.setUnitPrice(new BigDecimal("200.23"));

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceItemId(3);
        invoiceItem3.setInvoiceId(1);
        invoiceItem3.setInventoryId(4);
        invoiceItem3.setQuantity(2);
        invoiceItem3.setUnitPrice(new BigDecimal("300.54"));

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        invoiceItems.add(invoiceItem1);
        invoiceItems.add(invoiceItem2);
        invoiceItems.add(invoiceItem3);

        //Object to be returned
        InvoiceViewModel invoice = new InvoiceViewModel();

        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,12,13));
        invoice.setCustomerId(1);
        invoice.setInvoiceItems(invoiceItems);

        doReturn(invoice).when(invoiceService).createInvoice(invoiceC);
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

        //doThrow(new RuntimeException()).when(levelUpService).getLevelUpAccountByCustomerId(1);

    }

    public void setCustomerServiceMock(){
        customerService = mock(CustomerService.class);

        CustomerViewModel customer = new CustomerViewModel();

        customer.setCustomerId(1);
        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

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

    }

    public void setUpInventoryServiceMock(){
        inventoyService = mock(InventoyService.class);

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

        doReturn(listInventoryProduct1).when(inventoyService).getAllInventorysByProductId(1);
        doReturn(listInventoryProduct2).when(inventoyService).getAllInventorysByProductId(2);

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



        doNothing().when(inventoyService).updateInventory(inventory3Updated);
        doNothing().when(inventoyService).updateInventory(inventory4Updated);
        doNothing().when(inventoyService).updateInventory(inventory1Updated);

    }

    @Test
    public void processOrder() {
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

        ovm = serviceLayer.processOrder(ovm);
    }

    @Test
    public void filteredProductsToBuy(){
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

        System.out.println("Hello");


    }

    @Test
    public void orderInventoryListByQuantity() {
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

        List<InventoryViewModel> ordered3 = serviceLayer.orderInventoryListByQuantity(thirdList);

        List<InventoryViewModel> ordered2 = serviceLayer.orderInventoryListByQuantity(anotherList);

        List<InventoryViewModel> ordered = serviceLayer.orderInventoryListByQuantity(inventoryViewModelList);

        System.out.println("This");

    }
}