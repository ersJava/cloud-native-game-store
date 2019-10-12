package com.company.adminapiservice.controller;

import com.company.adminapiservice.exception.UpdateNotAllowedException;
import com.company.adminapiservice.service.ServiceLayer;
import com.company.adminapiservice.service.ServiceLayerAdmin;
import com.company.adminapiservice.viewmodel.CustomerViewModel;
import com.company.adminapiservice.viewmodel.FrontEndCustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class AdminApiController {

    @Autowired
    ServiceLayer serviceLayer;

    @Autowired
    ServiceLayerAdmin serviceLayerAdmin;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////CUSTOMER ENDPOINTS/////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customerFrontEnd
    //Create a Customer receiving a FrontEndCustomerViewModel
    @RequestMapping(value = "/customerFrontEnd", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FrontEndCustomerViewModel createCostumerFrontEnd(@RequestBody FrontEndCustomerViewModel fecvm) {

        return serviceLayerAdmin.createCustomer(fecvm);
    }

    //Reading a FrontEndCustomerViewModel
    @RequestMapping(value = "/customerFrontEnd/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public FrontEndCustomerViewModel getCostumerFrontEnd(@PathVariable int id) {

        return serviceLayerAdmin.getCustomerFrontEnd(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customer
    //Create a Customer receiving a CustomerViewModel
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel createCostumer(@RequestBody CustomerViewModel cvm) {

        return serviceLayerAdmin.createCustomer(cvm);
    }

    //Get aall CustomerViewModel
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCostumer() {

        return serviceLayerAdmin.getAllCustomers();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //uri: /customer{id}
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCostumer(@PathVariable int id) {

        return serviceLayerAdmin.getCustomer(id);
    }

    //Update CustomerViewModel
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCostumer(@PathVariable int id, @RequestBody CustomerViewModel cvm) {

        if(id != cvm.getCustomerId()){
            throw new UpdateNotAllowedException("Update not allowed, the id in the PathVariable does not match with the id in the RequestBody");
        }else{
            serviceLayerAdmin.updateCustomer(cvm);
        }
    }

    //Delete CustomerViewModel
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCostumer(@PathVariable int id) {

        serviceLayerAdmin.deleteCustomer(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////INVENTORY ENDPOINTS////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    


}
