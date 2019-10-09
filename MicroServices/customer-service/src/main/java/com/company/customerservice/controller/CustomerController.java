package com.company.customerservice.controller;

import com.company.customerservice.service.ServiceLayer;
import com.company.customerservice.viewmodel.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class CustomerController {

    @Autowired
    ServiceLayer serviceLayer;


    //uri: /customer
    //Create a Customer
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    CustomerViewModel newCustomer(@RequestBody CustomerViewModel cvm){
        return serviceLayer.newCustomer(cvm);
    }

    //Read all Customers
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<CustomerViewModel> getAllCustomers(){
        return serviceLayer.getAllCustomers();
    }

    //Update a Customer
    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    void updateCustomer(@RequestBody CustomerViewModel cvm){
        serviceLayer.updateCustomer(cvm);
    }

    //uri: /customer/{id}
    //Read a Customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    CustomerViewModel getCustomer(@PathVariable int id){
        return serviceLayer.getCustomer(id);
    }

    //Delete a Customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deleteCustomer(@PathVariable int id){
        serviceLayer.deleteCustomer(id);
    }

    //uri: /customer/{last_name}
    @RequestMapping(value = "/customer/findByLastName/{last_name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<CustomerViewModel> getCustomersByLastName(@PathVariable(name = "last_name") String lastName){

        return serviceLayer.getCustomersByLastName(lastName);
    }


}
