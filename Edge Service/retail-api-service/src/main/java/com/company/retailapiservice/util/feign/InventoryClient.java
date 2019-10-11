package com.company.retailapiservice.util.feign;

import com.company.retailapiservice.viewmodel.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient("inventory-client")
public interface InventoryClient {

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    List<InventoryViewModel> getAllInventories();

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    InventoryViewModel getInventory(@PathVariable("id") Integer id);

    // need this to update quantity when purchase is made
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    void updateInventory(@PathVariable("id") Integer id, @RequestBody @Valid InventoryViewModel ivm);

    @RequestMapping(value = "/inventory/product/{productId}", method = RequestMethod.GET)
    List<InventoryViewModel> getAllInventoriesByProductId(@PathVariable("productId") Integer productId);

}
