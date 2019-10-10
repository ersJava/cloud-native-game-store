package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.ProductViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductService {

    //Create a new Product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    ProductViewModel newProduct(@RequestBody ProductViewModel pvm);

    //Get all products
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    List<ProductViewModel> getAllProducts();

    //Update a existing Product
    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    void updateProduct(@RequestBody ProductViewModel pvm);

    //uri: /product/{id}
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    ProductViewModel getProduct(@PathVariable int id);

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    void deleteProduct(@PathVariable int id);

    //uri: /product/{product_name}
    ///////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/product/findByProductName/{product_name}", method = RequestMethod.GET)
    List<ProductViewModel> getProductsByProductName(@PathVariable(name = "product_name") String productName);

}
