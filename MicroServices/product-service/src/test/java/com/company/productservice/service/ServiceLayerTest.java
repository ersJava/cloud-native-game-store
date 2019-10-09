package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.dao.ProductDaoJdbcTemplateImpl;
import com.company.productservice.model.Product;
import com.company.productservice.viewmodel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ProductDao productDao;

    ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

        setUpProductDaoMock();

        serviceLayer = new ServiceLayer(productDao);
    }

    public void setUpProductDaoMock(){
        productDao = mock(ProductDaoJdbcTemplateImpl.class);

        Product product = new Product();

        product.setProductId(1);
        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice(new BigDecimal("250.36"));
        product.setUnitCost(new BigDecimal("200.11"));

        //Calling object
        Product productC = new Product();

        productC.setProductName("X1 Carbon");
        productC.setProductDescription("Lenovo laptop");
        productC.setListPrice(new BigDecimal("250.36"));
        productC.setUnitCost(new BigDecimal("200.11"));

        doReturn(product).when(productDao).createProduct(productC);
        doReturn(product).when(productDao).readProduct(1);

        //Creating a second register
        Product product2 = new Product();

        product2.setProductId(2);
        product2.setProductName("Contigo");
        product2.setProductDescription("Water container");
        product2.setListPrice(new BigDecimal("25.13"));
        product2.setUnitCost(new BigDecimal("22.00"));

        //List with all the products
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        List<Product> productListContigo = new ArrayList<>();
        productListContigo.add(product2);

        doReturn(productList).when(productDao).readAllProducts();
        doReturn(productListContigo).when(productDao).getProductsByProductName("Contigo");

        //toUpdate
        Product product2Update = new Product();

        product2Update.setProductId(5);
        product2Update.setProductName("Contigo");
        product2Update.setProductDescription("New version 2019 of the Water container");
        product2Update.setListPrice(new BigDecimal("25.13"));
        product2Update.setUnitCost(new BigDecimal("22.11"));

        doNothing().when(productDao).updateProduct(product2Update);
        doReturn(product2Update).when(productDao).readProduct(5);

        //ToDelete

        doNothing().when(productDao).deleteProduct(7);
        doReturn(null).when(productDao).readProduct(7);
    }

    @Test
    public void addGetProductTest(){
        ProductViewModel product = new ProductViewModel();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice("250.36");
        product.setUnitCost("200.11");

        product = serviceLayer.newProduct(product);

        //Reading the just added Product Register

        ProductViewModel fromService = serviceLayer.getProduct(product.getProductId());

        assertEquals(product, fromService);
    }

    @Test
    public void getAllProducts() {

        List<ProductViewModel> productList = serviceLayer.getAllProducts();

        assertEquals(productList.size(), 2);
    }

    @Test
    public void updateProduct() {
        ProductViewModel product2Update = new ProductViewModel();

        product2Update.setProductId(5);
        product2Update.setProductName("Contigo");
        product2Update.setProductDescription("New version 2019 of the Water container");
        product2Update.setListPrice("25.13");
        product2Update.setUnitCost("22.11");

        serviceLayer.updateProduct(product2Update);

        ProductViewModel fromService = serviceLayer.getProduct(product2Update.getProductId());

        assertEquals(product2Update, fromService);
    }

    @Test
    public void deleteProduct() {
        //Assuming that there is a Product with Id 7 in the database
        serviceLayer.deleteProduct(7);

        ProductViewModel fromService = serviceLayer.getProduct(7);

        assertNull(fromService);
    }

    @Test
    public void getProductsByProductName() {

        List<ProductViewModel> productListContigo = serviceLayer.getProductsByProductName("Contigo");

        assertEquals(productListContigo.size(), 1);
    }
}