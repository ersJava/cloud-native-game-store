package com.company.adminapiservice.service;

import com.company.adminapiservice.exception.LevelUpNotFoundException;
import com.company.adminapiservice.util.feign.*;
import com.company.adminapiservice.viewmodel.CustomerViewModel;
import com.company.adminapiservice.viewmodel.FrontEndCustomerViewModel;
import com.company.adminapiservice.viewmodel.LevelUpViewModel;
import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerAdminCustomerTest {

    CustomerService customerService;
    InventoryService inventoryService;
    InvoiceService invoiceService;
    LevelUpService levelUpService;
    ProductService productService;

    ServiceLayerAdmin serviceLayerAdmin;

    @Before
    public void setUp() throws Exception {

        setUpCustomerServiceMock();

        setUpLevelUpServiceMock();

        serviceLayerAdmin = new ServiceLayerAdmin(customerService, inventoryService, invoiceService,
                levelUpService, productService);
    }

    public void setUpLevelUpServiceMock(){
        levelUpService = mock(LevelUpService.class);

        LevelUpViewModel luvm = new LevelUpViewModel();
        luvm.setLevelUpId(1);
        luvm.setCustomerId(1);
        luvm.setMemberDate(LocalDate.of(2019,10,11));
        luvm.setPoints(10);

        //Calling Object
        LevelUpViewModel luvmC = new LevelUpViewModel();

        luvmC.setCustomerId(1);
        luvmC.setPoints(0);
        luvmC.setMemberDate(LocalDate.of(2019,10,11));

        doReturn(luvm).when(levelUpService).createLevelUpAccount(luvmC);
        doThrow(new LevelUpNotFoundException("Level Up not found for Customer 1")).when(levelUpService).getLevelUpAccountByCustomerId(1);
        doThrow(new LevelUpNotFoundException("Level Up not found for Customer 2")).when(levelUpService).getLevelUpAccountByCustomerId(2);

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

        //Calling Object
        CustomerViewModel customerC = new CustomerViewModel();

        customerC.setFirstName("Angel");
        customerC.setLastName("Lozada");
        customerC.setStreet("Los Naranjos");
        customerC.setCity("San Cristobal");
        customerC.setZip("5001");
        customerC.setEmail("lzda.dave@gmail.com");
        customerC.setPhone("718-963-895");

        //Mock methods for create and get
        doReturn(customer).when(customerService).newCustomer(customerC);
        doReturn(customer).when(customerService).getCustomer(1);

        ///////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////
        CustomerViewModel customer2 = new CustomerViewModel();

        customer2.setCustomerId(2);
        customer2.setFirstName("Dave");
        customer2.setLastName("Guerrero");
        customer2.setStreet("Los Naranjos");
        customer2.setCity("San Cristobal");
        customer2.setZip("5001");
        customer2.setEmail("lzda.dave@gmail.com");
        customer2.setPhone("718-963-895");

        //Calling Object
        CustomerViewModel customer2C = new CustomerViewModel();

        customer2C.setFirstName("Dave");
        customer2C.setLastName("Guerrero");
        customer2C.setStreet("Los Naranjos");
        customer2C.setCity("San Cristobal");
        customer2C.setZip("5001");
        customer2C.setEmail("lzda.dave@gmail.com");
        customer2C.setPhone("718-963-895");

        doReturn(customer2).when(customerService).newCustomer(customer2C);
        doReturn(customer2).when(customerService).getCustomer(2);

        //Mock methods for the return allCustomers
        List<CustomerViewModel> customerViewModelList = new ArrayList<>();
        customerViewModelList.add(customer);
        customerViewModelList.add(customer2);

        doReturn(customerViewModelList).when(customerService).getAllCustomers();

        //Mock methods for return allCustomers by lastNam
        List<CustomerViewModel> customerLastNameList = new ArrayList<>();
        customerLastNameList.add(customer2);

        doReturn(customerLastNameList).when(customerService).getCustomersByLastName("Guerrero");

        //Mock methods for the updateCustomer
        CustomerViewModel customer2Updated = new CustomerViewModel();

        customer2Updated.setCustomerId(5);
        customer2Updated.setFirstName("Dave");
        customer2Updated.setLastName("Guerrero");
        customer2Updated.setStreet("THIS IS AN UPDATED ADDRESS");
        customer2Updated.setCity("San Cristobal");
        customer2Updated.setZip("5001");
        customer2Updated.setEmail("lzda.dave@gmail.com");
        customer2Updated.setPhone("718-963-895");

        doNothing().when(customerService).updateCustomer(customer2Updated);
        doReturn(customer2Updated).when(customerService).getCustomer(5);
    }

    @Test
    public void createCustomer() {
        CustomerViewModel customer = new CustomerViewModel();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        FrontEndCustomerViewModel fecvm = new FrontEndCustomerViewModel();
        fecvm.setCustomerViewModel(customer);
        fecvm.setJoinToLevelUp(true);
        fecvm.setCreationDate(LocalDate.of(2019,10,11));

        //Creating a Customer
        fecvm = serviceLayerAdmin.createCustomer(fecvm);

        ///////////////////////////////////////////////////////////////
        //Customer object EXPECTED from the ServiceLayer
        CustomerViewModel fromService = new CustomerViewModel();

        fromService.setCustomerId(1);
        fromService.setFirstName("Angel");
        fromService.setLastName("Lozada");
        fromService.setStreet("Los Naranjos");
        fromService.setCity("San Cristobal");
        fromService.setZip("5001");
        fromService.setEmail("lzda.dave@gmail.com");
        fromService.setPhone("718-963-895");

        LevelUpViewModel luvm = new LevelUpViewModel();
        luvm.setLevelUpId(1);
        luvm.setCustomerId(1);
        luvm.setMemberDate(LocalDate.of(2019,10,11));
        luvm.setPoints(10);

        FrontEndCustomerViewModel fromServiceFecvm = new FrontEndCustomerViewModel();

        fromServiceFecvm.setCustomerViewModel(fromService);
        fromServiceFecvm.setCreationDate(LocalDate.of(2019,10,11));
        fromServiceFecvm.setJoinToLevelUp(true);
        fromServiceFecvm.setLevelUpAccount(luvm);

        //Making sure that they are equal
        assertEquals(fecvm, fromServiceFecvm);

        //////////////////////////////////////////////////////////////////////////////////
        ///////////TESTING WHEN THE USER DON'T WANT TO BE PART OF LEVELUP/////////////////
        CustomerViewModel customer2 = new CustomerViewModel();

        customer2.setFirstName("Dave");
        customer2.setLastName("Guerrero");
        customer2.setStreet("Los Naranjos");
        customer2.setCity("San Cristobal");
        customer2.setZip("5001");
        customer2.setEmail("lzda.dave@gmail.com");
        customer2.setPhone("718-963-895");

        FrontEndCustomerViewModel fromServiceFecvm2 = new FrontEndCustomerViewModel();

        fromServiceFecvm2.setCustomerViewModel(customer2);
        fromServiceFecvm2.setCreationDate(null);
        fromServiceFecvm2.setLevelUpAccount(null);
        fromServiceFecvm2.setJoinToLevelUp(false);

        //Creating a Customer
        fromServiceFecvm2 = serviceLayerAdmin.createCustomer(fromServiceFecvm2);

        ///////////////////////////////////////////////////////////////
        //Customer object EXPECTED from the ServiceLayer
        FrontEndCustomerViewModel expectedFromService = serviceLayerAdmin.getCustomerFrontEnd(2);

        //Making sure that they are equal
        assertEquals(fromServiceFecvm2, expectedFromService);

    }

    @Test
    public void getFrontEndCustomerTest(){

        //Expected Customer from the Service
        CustomerViewModel customer2 = new CustomerViewModel();

        customer2.setCustomerId(2);
        customer2.setFirstName("Dave");
        customer2.setLastName("Guerrero");
        customer2.setStreet("Los Naranjos");
        customer2.setCity("San Cristobal");
        customer2.setZip("5001");
        customer2.setEmail("lzda.dave@gmail.com");
        customer2.setPhone("718-963-895");

        FrontEndCustomerViewModel fecvm = new FrontEndCustomerViewModel();
        fecvm.setCustomerViewModel(customer2);
        fecvm.setCreationDate(null);
        fecvm.setLevelUpAccount(null);
        fecvm.setJoinToLevelUp(false);

        /////////////////////////////////////////////////////////////////////////////
        //Read the Customer from the service
        FrontEndCustomerViewModel fecvmFromService = serviceLayerAdmin.getCustomerFrontEnd(customer2.getCustomerId());

        assertEquals(fecvm, fecvmFromService);
    }

    @Test
    public void getCustomerViewModelTest(){
        CustomerViewModel customer = new CustomerViewModel();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        //Creating a CustomerViewModel
        customer = serviceLayerAdmin.createCustomer(customer);

        //Reading a CustomerViewModel
        CustomerViewModel fromService = serviceLayerAdmin.getCustomer(customer.getCustomerId());

        assertEquals(customer, fromService);
    }

    @Test
    public void getAllCustomers(){
        List<CustomerViewModel> fromService = customerService.getAllCustomers();

        assertEquals(fromService.size(), 2);
    }

    @Test
    public void updateCustomer() {
        CustomerViewModel customer2Updated = new CustomerViewModel();

        customer2Updated.setCustomerId(5);
        customer2Updated.setFirstName("Dave");
        customer2Updated.setLastName("Guerrero");
        customer2Updated.setStreet("THIS IS AN UPDATED ADDRESS");
        customer2Updated.setCity("San Cristobal");
        customer2Updated.setZip("5001");
        customer2Updated.setEmail("lzda.dave@gmail.com");
        customer2Updated.setPhone("718-963-895");

        serviceLayerAdmin.updateCustomer(customer2Updated);

        //Reading the updated CustomerViewModel from the service
        CustomerViewModel fromService = serviceLayerAdmin.getCustomer(customer2Updated.getCustomerId());

        assertEquals(customer2Updated, fromService);
    }


    @Test
    public void getCustomersByLastName() {
        List<CustomerViewModel> fromService = customerService.getCustomersByLastName("Guerrero");

        assertEquals(fromService.size(), 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Test for Delete Customer is in ServiceLayerAdminDeleteCustomerProductTest
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//
}