package com.company.adminapiservice.service;

import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.CustomerViewModel;
import com.company.adminapiservice.viewmodel.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerAdminLevelUpTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayerAdmin serviceLayerAdmin;

    @Before
    public void setUp() throws Exception {

        setUpLevelUpServiceMock();

        setUpCustomerServiceMock();

        serviceLayerAdmin = new ServiceLayerAdmin(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpCustomerServiceMock(){
        customerService = mock(CustomerService.class);

        CustomerViewModel customer = new CustomerViewModel();

        customer.setCustomerId(1);
        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        doReturn(customer).when(customerService).getCustomer(10);
    }

    public void setUpLevelUpServiceMock(){
        levelUpService = mock(LevelUpService.class);

        LevelUpViewModel levelUp = new LevelUpViewModel();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        //Calling Object
        LevelUpViewModel levelUpC = new LevelUpViewModel();

        levelUpC.setCustomerId(10);
        levelUpC.setPoints(250);
        levelUpC.setMemberDate(LocalDate.of(2019, 8, 21));

        doReturn(levelUp).when(levelUpService).createLevelUpAccount(levelUpC);
        doReturn(levelUp).when(levelUpService).getLevelUpAccount(1);

        //Mock for getAllAccounts
        LevelUpViewModel levelUp2 = new LevelUpViewModel();
        levelUp2.setLevelUpId(2);
        levelUp2.setCustomerId(10);
        levelUp2.setPoints(250);
        levelUp2.setMemberDate(LocalDate.of(2019, 8, 21));

        List<LevelUpViewModel> allAccounts = new ArrayList<>();
        allAccounts.add(levelUp);
        allAccounts.add(levelUp2);

        doReturn(allAccounts).when(levelUpService).getAllLevelUpAccounts();

        //Mock for updatePointsOnAccount
        LevelUpViewModel levelUpUpdate = new LevelUpViewModel();
        levelUpUpdate.setLevelUpId(5);
        levelUpUpdate.setCustomerId(10);
        levelUpUpdate.setPoints(250);
        levelUpUpdate.setMemberDate(LocalDate.of(2019, 8, 21));

        doNothing().when(levelUpService).updatePointsOnAccount(levelUpUpdate.getCustomerId(), levelUpUpdate);
        doReturn(levelUpUpdate).when(levelUpService).getLevelUpAccount(5);

        //Mock for delete
        doNothing().when(levelUpService).deleteLevelUpAccount(7);
        doReturn(null).when(levelUpService).getLevelUpAccount(7);
    }

    @Test
    public void createGetLevelUpTest() {
        LevelUpViewModel levelUp = new LevelUpViewModel();

        levelUp.setCustomerId(10);
        levelUp.setPoints(250);
        levelUp.setMemberDate(LocalDate.of(2019, 8, 21));

        //Creating a new LevelUp account
        levelUp = serviceLayerAdmin.createLevelUp(levelUp);

        //Read the created account
        LevelUpViewModel fromService = serviceLayerAdmin.getLevelUpAccount(levelUp.getLevelUpId());

        assertEquals(levelUp,fromService);
    }

    @Test
    public void getAllLevelUpAccountsTest() {
        List<LevelUpViewModel> allAccounts = serviceLayerAdmin.getAllLevelUpAccounts();

        assertEquals(allAccounts.size(),2);
    }

    @Test
    public void deleteLevelUpAccountTest() {
        serviceLayerAdmin.deleteLevelUpAccount(7);

        LevelUpViewModel luvm = serviceLayerAdmin.getLevelUpAccount(7);

        assertNull(luvm);
    }

    @Test
    public void updatePointsOnAccount(){
        LevelUpViewModel levelUpUpdate = new LevelUpViewModel();
        levelUpUpdate.setLevelUpId(5);
        levelUpUpdate.setCustomerId(10);
        levelUpUpdate.setPoints(250);
        levelUpUpdate.setMemberDate(LocalDate.of(2019, 8, 21));

        serviceLayerAdmin.updatePointsInAccount(levelUpUpdate.getCustomerId(),levelUpUpdate);

        //Read the updated Account from the service
        LevelUpViewModel fromService = serviceLayerAdmin.getLevelUpAccount(levelUpUpdate.getLevelUpId());

        assertEquals(levelUpUpdate, fromService);
    }
}