package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.viewmodel.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("customer-service")
public interface CustomerClient {

//    @RequestMapping(value = "/customer", method = RequestMethod.GET)
//    List<CustomerViewModel> getAllCustomers();

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    CustomerViewModel getCustomer(@PathVariable int id);

}
