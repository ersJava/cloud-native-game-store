package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.viewmodel.ProductViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    List<ProductViewModel> getAllProducts();

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    ProductViewModel getProduct(@PathVariable int id);

    @RequestMapping(value = "/product/findByProductName/{product_name}", method = RequestMethod.GET)
    List<ProductViewModel> getProductsByProductName(@PathVariable(name = "product_name") String productName);

}
