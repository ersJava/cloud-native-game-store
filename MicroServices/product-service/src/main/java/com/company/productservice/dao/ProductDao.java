package com.company.productservice.dao;

import com.company.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    //Create
    Product createProduct(Product product);

    //Read
    Product readProduct(int productId);

    List<Product> readAllProducts();

    //Update
    void updateProduct(Product product);

    //Delete
    void deleteProduct(int productId);

    //ReadProductsByProductName
    List<Product> getProductsByProductName(String productName);
}
