package com.company.inventoryservice.service;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.exception.NotFoundException;
import com.company.inventoryservice.model.Inventory;
import com.company.inventoryservice.viewmodel.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private InventoryDao dao;

    @Autowired
    public ServiceLayer (InventoryDao dao) {
        this.dao = dao;
    }

    private InventoryViewModel buildViewModel(Inventory inventory) {

        InventoryViewModel ivm = new InventoryViewModel();
        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setProductId(inventory.getProductId());
        ivm.setQuantity(inventory.getQuantity());

        return ivm;
    }

    @Transactional
    public InventoryViewModel saveInventory(InventoryViewModel ivm) {

        Inventory inventory = new Inventory();
        inventory.setProductId(ivm.getProductId());
        inventory.setQuantity(ivm.getQuantity());
        inventory = dao.addInventory(inventory);

        ivm.setInventoryId(inventory.getInventoryId());

        return ivm;
    }

    public List<InventoryViewModel> findAllInventories() {

        List<Inventory> inventoryList = dao.getAllInventories();

        if(inventoryList.size() == 0){
            throw new NotFoundException("Database empty, No inventories found");
        }

        List<InventoryViewModel> lvmList = new ArrayList<>();

        for (Inventory i : inventoryList) {
            InventoryViewModel ivm = buildViewModel(i);
            lvmList.add(ivm);
        }

        return lvmList;
    }

    public InventoryViewModel findInventory(Integer id) {

        Inventory inventory = dao.getInventory(id);

        if (inventory == null)
            throw new NotFoundException(String.format("Inventory entry could not be retrieved for id %s", id));
        else
            return buildViewModel(inventory);
    }

    @Transactional
    public void updateInventory(InventoryViewModel ivm) {

        //Making sure that the Inventory Exist
        findInventory(ivm.getInventoryId());

        Inventory inventory = new Inventory();
        inventory.setInventoryId(ivm.getInventoryId());
        inventory.setProductId(ivm.getProductId());
        inventory.setQuantity(ivm.getQuantity());

        dao.updateInventory(inventory);
    }

    public void removeInventory(Integer id){

        Inventory inventory = dao.getInventory(id);

        if(inventory == null)
            throw new NotFoundException(String.format("Inventory entry could not be retrieved for id %s", id));

        dao.deleteInventory(id);
    }

    //  ---- Custom Method ----
    // Get Invoices by Product ID
    public List<InventoryViewModel> findInventoryByProductId(Integer productId) {

        List<Inventory> list = dao.getInventoryByProductId(productId);

        List<InventoryViewModel> ivmList = new ArrayList<>();

        if(list != null && list.size() == 0)
            throw new NotFoundException(String.format("No invoices in the system found with customer id %s", productId));
        else
            for (Inventory i : list) {
                InventoryViewModel ivm = buildViewModel(i);
                ivmList.add(ivm);
            }
        return ivmList;
    }
}
