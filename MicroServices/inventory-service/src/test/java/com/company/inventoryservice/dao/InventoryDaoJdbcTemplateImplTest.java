package com.company.inventoryservice.dao;

import com.company.inventoryservice.model.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoJdbcTemplateImplTest {

    @Autowired
    InventoryDao dao;

    @Before
    public void setUp() throws Exception {

        List<Inventory> allInventories = dao.getAllInventories();
        allInventories.forEach(inventory -> dao.deleteInventory(inventory.getInventoryId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addGetDeleteInventory() {

        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(7);

        inventory = dao.addInventory(inventory);

        Inventory fromService = dao.getInventory(inventory.getInventoryId());
        assertEquals(fromService, inventory);

        dao.deleteInventory(inventory.getInventoryId());
        fromService = dao.getInventory(inventory.getInventoryId());
        assertNull(fromService);

    }

    @Test
    public void getAllInventories() {

        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(7);

        dao.addInventory(inventory);

        List<Inventory> list = dao.getAllInventories();
        assertEquals(list.size(), 1);

    }

    @Test
    public void updateInventory() {

        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(7);
        inventory = dao.addInventory(inventory);

        inventory.setProductId(100);
        inventory.setQuantity(17);
        dao.updateInventory(inventory);

        Inventory fromService = dao.getInventory(inventory.getInventoryId());

        assertEquals(fromService, inventory);

    }

    @Test
    public void getInventoryByProductId() {

        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(7);
        dao.addInventory(inventory);

        List<Inventory> fromService = dao.getInventoryByProductId(100);
        assertEquals(fromService.size(), 1);

    }
}