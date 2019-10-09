package com.company.productservice.controller;

import com.company.productservice.service.ServiceLayer;
import com.company.productservice.viewmodel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class ProductController {

    @Autowired
    ServiceLayer serviceLayer;
    ///////////////////////////////////////////////////////////////////////
    //uri: /product
    ///////////////////////////////////////////////////////////////////////
    //Create a new Product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel newProduct(@RequestBody ProductViewModel pvm){
        return serviceLayer.newProduct(pvm);
    }

    //Get all products
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts(){
        return serviceLayer.getAllProducts();
    }

    //Update a existing Product
    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody ProductViewModel pvm){
        serviceLayer.updateProduct(pvm);
    }

    ///////////////////////////////////////////////////////////////////////
    //uri: /product/{id}
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel getProduct(@PathVariable int id){
        return serviceLayer.getProduct(id);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id){
        serviceLayer.deleteProduct(id);
    }

    ///////////////////////////////////////////////////////////////////////
    //uri: /product/{product_name}
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/product/findByProductName/{product_name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getProductsByProductName(@PathVariable(name = "product_name") String productName){
        return serviceLayer.getProductsByProductName(productName);
    }

}
