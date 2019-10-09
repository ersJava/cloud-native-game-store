package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.exception.ProductNotFoundException;
import com.company.productservice.model.Product;
import com.company.productservice.viewmodel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    ProductDao productDao;

    @Autowired
    public ServiceLayer(ProductDao productDao){
        this.productDao = productDao;
    }

    //uri: /product
    @Transactional
    public ProductViewModel newProduct(ProductViewModel pvm){
        return buildProductViewModel(productDao.createProduct(buildProduct(pvm)));
    }

    public List<ProductViewModel> getAllProducts(){
        List<Product> productList = productDao.readAllProducts();

        if(productList.size() == 0){
            throw new ProductNotFoundException("The database is empty!!! No Product(s) found in the Database");
        }else{
            return buildPVMList(productList);
        }
    }

    @Transactional
    public void updateProduct(ProductViewModel pvm){
        getProduct(pvm.getProductId());

        productDao.updateProduct(buildProduct(pvm));
    }

    //uri:
    public ProductViewModel getProduct(int productId){

        Product product = productDao.readProduct(productId);

        if(product == null){
            throw new ProductNotFoundException("No Product found in the database for id #" + productId + " !");

        }else{
            return buildProductViewModel(product);
        }
    }

    public void deleteProduct(int productId){
        //getProduct(productId); //Uncomment after testing

        productDao.deleteProduct(productId);
    }

    //uri:
    public List<ProductViewModel> getProductsByProductName(String productName){
        List<Product> productList = productDao.getProductsByProductName(productName);

        if(productList.size() == 0){
            throw new ProductNotFoundException("No Product(s) found with the productName: " + productName + "!");
        }else{
            return buildPVMList(productList);
        }
    }

    //Helper methods
    public Product buildProduct(ProductViewModel pvm){
        Product product = new Product();

        if(pvm == null){
            return null;
        }else{
            product.setProductId(pvm.getProductId());
            product.setProductName(pvm.getProductName());
            product.setProductDescription(pvm.getProductDescription());
            product.setListPrice(BigDecimal.valueOf(Double.valueOf(pvm.getListPrice())));
            product.setUnitCost(BigDecimal.valueOf(Double.valueOf(pvm.getUnitCost())));
        }

        return product;
    }

    public ProductViewModel buildProductViewModel(Product product){
        ProductViewModel pvm = new ProductViewModel();

        if (product == null) {
            return null;
        }else{
            pvm.setProductId(product.getProductId());
            pvm.setProductName(product.getProductName());
            pvm.setProductDescription(product.getProductDescription());
            pvm.setListPrice(product.getListPrice().toString());
            pvm.setUnitCost(product.getUnitCost().toString());
        }
        return pvm;
    }

    public List<ProductViewModel> buildPVMList(List<Product> productList){
        List<ProductViewModel> pvmList = new ArrayList<>();

        productList.stream().forEach(product -> pvmList.add(buildProductViewModel(product)));

        return pvmList;
    }
}
