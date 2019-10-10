package com.company.inventoryservice.controller;

import com.company.inventoryservice.service.ServiceLayer;
import com.company.inventoryservice.viewmodel.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ServiceLayer serviceLayer;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel ivm) {

        return serviceLayer.saveInventory(ivm);
    }

    // Read all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventories() {

        return serviceLayer.findAllInventories();
    }

    // Read by Id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryViewModel getInventory(@PathVariable("id") Integer id) {

        return serviceLayer.findInventory(id);
    }

    // Update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable("id") Integer id, @RequestBody @Valid InventoryViewModel ivm) {

        if(!id.equals(ivm.getInventoryId())) {
            throw new IllegalArgumentException(String.format("Id %s in the PathVariable does not match the Id %s in the RequestBody ", id, ivm.getInventoryId()));
        }
        serviceLayer.updateInventory(ivm);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") Integer id) {

       serviceLayer.removeInventory(id);
    }

    // Custom method - Get inventory entry by product Id
    @GetMapping("/byProductId/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventoriesByProductId(@PathVariable("productId") Integer productId) {

        return serviceLayer.findInventoryByProductId(productId);

    }
}
