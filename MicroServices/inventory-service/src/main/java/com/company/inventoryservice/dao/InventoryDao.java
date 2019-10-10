package com.company.inventoryservice.dao;

import com.company.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {

    // Basic CRUD

    Inventory addInventory(Inventory inventory);

    Inventory getInventory(int id);

    List<Inventory> getAllInventories();

    void updateInventory(Inventory inventory);

    void deleteInventory(int id);

    // Custom Method
    List<Inventory> getInventoryByProductId(int productId);
}
