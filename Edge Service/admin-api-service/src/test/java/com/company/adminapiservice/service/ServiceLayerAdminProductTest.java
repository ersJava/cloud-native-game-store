package com.company.adminapiservice.service;

import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerAdminProductTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayerAdmin serviceLayerAdmin;

    @Before
    public void setUp() throws Exception {

        setUpProductServiceMock();

        serviceLayerAdmin = new ServiceLayerAdmin(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpProductServiceMock(){
        productService = mock(ProductService.class);

        ProductViewModel product = new ProductViewModel();

        product.setProductId(1);
        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice("250.36");
        product.setUnitCost("200.11");

        //Calling object
        ProductViewModel productC = new ProductViewModel();

        productC.setProductName("X1 Carbon");
        productC.setProductDescription("Lenovo laptop");
        productC.setListPrice("250.36");
        productC.setUnitCost("200.11");

        doReturn(product).when(productService).newProduct(productC);
        doReturn(product).when(productService).getProduct(1);

        //Mock for getAllProducts
        ProductViewModel product2 = new ProductViewModel();

        product2.setProductId(2);
        product2.setProductName("X1 Carbon");
        product2.setProductDescription("Lenovo laptop");
        product2.setListPrice("250.36");
        product2.setUnitCost("200.11");

        List<ProductViewModel> allProducts = new ArrayList<>();
        allProducts.add(product);
        allProducts.add(product2);

        doReturn(allProducts).when(productService).getAllProducts();

        //Mock for update
        ProductViewModel product2Update = new ProductViewModel();

        product2Update.setProductId(5);
        product2Update.setProductName("X1 Carbon");
        product2Update.setProductDescription("Lenovo laptop");
        product2Update.setListPrice("250.36");
        product2Update.setUnitCost("200.11");

        doNothing().when(productService).updateProduct(product2Update);
        doReturn(product2Update).when(productService).getProduct(5);


    }

    @Test
    public void createGetProductTest() {
        ProductViewModel product = new ProductViewModel();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice("250.36");
        product.setUnitCost("200.11");

        //Creating a new Product
        product = serviceLayerAdmin.createProduct(product);

        //Reading the product from the Service
        ProductViewModel fromService = serviceLayerAdmin.getProduct(product.getProductId());

        assertEquals(product, fromService);
    }

    @Test
    public void getAllProductsTest() {
        List<ProductViewModel> allProducts = serviceLayerAdmin.getAllProducts();

        assertEquals(allProducts.size(),2);
    }

    @Test
    public void updateProductTest() {
        ProductViewModel product2Update = new ProductViewModel();

        product2Update.setProductId(5);
        product2Update.setProductName("X1 Carbon");
        product2Update.setProductDescription("Lenovo laptop");
        product2Update.setListPrice("250.36");
        product2Update.setUnitCost("200.11");

        serviceLayerAdmin.updateProduct(product2Update);

        //Read the updated Product
        ProductViewModel fromService = serviceLayerAdmin.getProduct(product2Update.getProductId());

        //Making sure that they are equal
        assertEquals(product2Update,fromService);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Test for Delete Product is in ServiceLayerAdminDeleteCustomerProductTest
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//
}