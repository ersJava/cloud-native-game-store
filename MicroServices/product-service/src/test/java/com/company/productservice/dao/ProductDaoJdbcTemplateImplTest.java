package com.company.productservice.dao;

import com.company.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoJdbcTemplateImplTest {

    @Autowired
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        List<Product> productList = productDao.readAllProducts();

        productList.stream().forEach(product -> productDao.deleteProduct(product.getProductId()));
    }

    @Test
    public void addGetDeleteProduct(){

        Product product = new Product();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice(new BigDecimal("250.36"));
        product.setUnitCost(new BigDecimal("200.00"));

        //Creating a new register for Product in the database
        product = productDao.createProduct(product);

        //Reading the just added register
        Product fromDatabase = productDao.readProduct(product.getProductId());

        //Asserting that they are equal
        assertEquals(product, fromDatabase);

        //Deleting the added product
        productDao.deleteProduct(product.getProductId());

        fromDatabase = productDao.readProduct(product.getProductId());

        assertNull(fromDatabase);
    }

    @Test
    public void readAllProducts() {

        Product product = new Product();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice(new BigDecimal("250.36"));
        product.setUnitCost(new BigDecimal("200.00"));

        //Creating a new register for Product in the database
        product = productDao.createProduct(product);

        //Creating a second register
        Product product2 = new Product();

        product2.setProductName("Contigo");
        product2.setProductDescription("Water container");
        product2.setListPrice(new BigDecimal("25.13"));
        product2.setUnitCost(new BigDecimal("22.00"));

        //Creating a new register for Product in the database
        product2 = productDao.createProduct(product2);

        List<Product> productList = productDao.readAllProducts();

        assertEquals(productList.size(), 2);
    }

    @Test
    public void updateProduct() {
        Product product = new Product();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice(new BigDecimal("250.36"));
        product.setUnitCost(new BigDecimal("200.00"));

        //Creating a new register for Product in the database
        product = productDao.createProduct(product);

        //updating the description
        product.setProductDescription("7th Generation Lenovo Laptop");
        productDao.updateProduct(product);

        //Reading the updated Product from the database
        Product fromDatabase = productDao.readProduct(product.getProductId());

        assertEquals(product, fromDatabase);
    }

    @Test
    public void getProductsByProductName() {
        Product product = new Product();

        product.setProductName("X1 Carbon");
        product.setProductDescription("Lenovo laptop");
        product.setListPrice(new BigDecimal("250.36"));
        product.setUnitCost(new BigDecimal("200.00"));

        //Creating a new register for Product in the database
        product = productDao.createProduct(product);

        //Creating a second register
        Product product2 = new Product();

        product2.setProductName("Contigo");
        product2.setProductDescription("Water container");
        product2.setListPrice(new BigDecimal("25.13"));
        product2.setUnitCost(new BigDecimal("22.00"));

        //Creating a new register for Product in the database
        product2 = productDao.createProduct(product2);

        //Reading the products with the name Contigo
        List<Product> productList = productDao.getProductsByProductName("Contigo");

        assertEquals(productList.size(),1);
    }
}