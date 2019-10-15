package com.company.adminapiservice.util.feign;

import com.company.adminapiservice.viewmodel.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryService {

    //uri: /inventory
    //Create inventory registry
    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    InventoryViewModel createInventory(@RequestBody InventoryViewModel ivm);

    //Get all the inventory registers
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    List<InventoryViewModel> getAllInventories();

    //Update an Inventory Registry
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    void updateInventory(@PathVariable("id") Integer id, @RequestBody InventoryViewModel ivm);

    //uri: /inventory/{id}
    //Get Inventory for the inventoryId
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    InventoryViewModel getInventory(@PathVariable int id);

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
    void deleteInventory(@PathVariable int id);

    //uri: /inventory/byProductId/{id}
    @RequestMapping(value = "/inventory/product/{productId}", method = RequestMethod.GET)
    List<InventoryViewModel> getAllInventoriesByProductId(@PathVariable int productId);
}
