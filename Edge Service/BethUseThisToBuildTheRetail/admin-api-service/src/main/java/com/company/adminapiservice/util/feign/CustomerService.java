package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerService {

    //uri: /customer
    //Create a Customer
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    CustomerViewModel newCustomer(@RequestBody CustomerViewModel cvm);

    //Read all Customers
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    List<CustomerViewModel> getAllCustomers();

    //Update a Customer
    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    void updateCustomer(@RequestBody CustomerViewModel cvm);

    //uri: /customer/{id}
    //Read a Customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    CustomerViewModel getCustomer(@PathVariable int id);

    //Delete a Customer
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    void deleteCustomer(@PathVariable int id);

    //uri: /customer/{last_name}
    @RequestMapping(value = "/customer/findByLastName/{last_name}", method = RequestMethod.GET)
    List<CustomerViewModel> getCustomersByLastName(@PathVariable(name = "last_name") String lastName);
}
