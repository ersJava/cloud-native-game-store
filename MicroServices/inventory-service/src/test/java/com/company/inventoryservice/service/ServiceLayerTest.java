package com.company.inventoryservice.service;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.company.inventoryservice.exception.NotFoundException;
import com.company.inventoryservice.model.Inventory;
import com.company.inventoryservice.viewmodel.InventoryViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer serviceLayer;

    private InventoryDao dao;

    @Before
    public void setUp() throws  Exception {

        setUpInventoryMock();

        serviceLayer = new ServiceLayer(dao);
    }

    private void setUpInventoryMock() {

        dao = mock(InventoryDaoJdbcTemplateImpl.class);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(100);
        inventory.setQuantity(7);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(100);
        inventory1.setQuantity(7);

        // update Mock data
        Inventory updateInventory = new Inventory();
        updateInventory.setInventoryId(9);
        updateInventory.setProductId(9999);
        updateInventory.setQuantity(99);

        // Mock save
        doReturn(inventory).when(dao).addInventory(inventory1);

        // Mock getAll
        List<Inventory> list = new ArrayList<>();
        list.add(inventory);
        doReturn(list).when(dao).getAllInventories();

        // Mock getbyId
        doReturn(inventory).when(dao).getInventory(1);

        // Mock delete
        doNothing().when(dao).deleteInventory(55);
        doReturn(null).when(dao).getInventory(55);

        // Mock update
        doNothing().when(dao).updateInventory(updateInventory);
        doReturn(updateInventory).when(dao).getInventory(9);

        // Custom Method

        // Mock getByProductId
        doReturn(list).when(dao).getInventoryByProductId(100);

    }

    @Test
    public void saveFindInventory() {

        InventoryViewModel ivm = new InventoryViewModel();
        ivm.setProductId(100);
        ivm.setQuantity(7);

        ivm = serviceLayer.saveInventory(ivm);

        InventoryViewModel ivm2 = serviceLayer.findInventory(ivm.getInventoryId());

        assertEquals(ivm, ivm2);
    }

    @Test
    public void findAllInventories() {

        List<InventoryViewModel> list = serviceLayer.findAllInventories();
        assertEquals(1, list.size());
    }

    @Test
    public void updateInventory() {

        InventoryViewModel updateInventory = new InventoryViewModel();
        updateInventory.setInventoryId(9);
        updateInventory.setProductId(9999);
        updateInventory.setQuantity(99);

        serviceLayer.updateInventory(updateInventory);

        InventoryViewModel afterUpdate = serviceLayer.findInventory(updateInventory.getInventoryId());

        assertEquals(updateInventory, afterUpdate);

    }

    @Test(expected = NotFoundException.class)
    public void removeInventory() {

        serviceLayer.removeInventory(55);

        InventoryViewModel ivm = serviceLayer.findInventory(55);

    }

    @Test
    public void findInventoryByProductId() {

        InventoryViewModel ivm = new InventoryViewModel();
        ivm.setInventoryId(1);
        ivm.setProductId(100);
        ivm.setQuantity(7);
        serviceLayer.saveInventory(ivm);

        List<InventoryViewModel> list = serviceLayer.findInventoryByProductId(100);

        assertEquals(list.size(), 1);
    }
}