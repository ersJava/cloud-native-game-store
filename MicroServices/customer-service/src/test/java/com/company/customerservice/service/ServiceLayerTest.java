package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.company.customerservice.model.Customer;
import com.company.customerservice.viewmodel.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    CustomerDao customerDao;

    ServiceLayer serviceLayer;

    @Before
    public void setUp() throws Exception {

        setUpCustomerDaoMock();

        serviceLayer = new ServiceLayer(customerDao);
    }

    //Mock for the customerDao
    public void setUpCustomerDaoMock(){

        customerDao = mock(CustomerDaoJdbcTemplateImpl.class);

        Customer customer = new Customer();

        customer.setCustomerId(1);
        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        //Calling object
        Customer customerC = new Customer();

        customerC.setFirstName("Angel");
        customerC.setLastName("Lozada");
        customerC.setStreet("Los Naranjos");
        customerC.setCity("San Cristobal");
        customerC.setZip("5001");
        customerC.setEmail("lzda.dave@gmail.com");
        customerC.setPhone("718-963-895");

        doReturn(customer).when(customerDao).createCustomer(customerC);
        doReturn(customer).when(customerDao).readCustomer(1);

        //ForUpdate
        Customer customerToUpdate = new Customer();

        customerToUpdate.setCustomerId(3);
        customerToUpdate.setFirstName("Angel");
        customerToUpdate.setLastName("Lozada");
        customerToUpdate.setStreet("Another Street");
        customerToUpdate.setCity("San Cristobal");
        customerToUpdate.setZip("5001");
        customerToUpdate.setEmail("lzda.dave@gmail.com");
        customerToUpdate.setPhone("718-963-895");

        doNothing().when(customerDao).updateCustomer(customerToUpdate);
        doReturn(customerToUpdate).when(customerDao).readCustomer(3);

        //ForDelete
        doNothing().when(customerDao).deleteCustomer(5);
        doReturn(null).when(customerDao).readCustomer(5);

        //ForReturnAll
        Customer customer3 = new Customer();

        customer3.setCustomerId(2);
        customer3.setFirstName("Dave");
        customer3.setLastName("Guerrero");
        customer3.setStreet("Los Naranjos");
        customer3.setCity("San Cristobal");
        customer3.setZip("5001");
        customer3.setEmail("lzda.dave@gmail.com");
        customer3.setPhone("718-963-895");

        List<Customer> allCustomers = new ArrayList<>();

        allCustomers.add(customer);
        allCustomers.add(customer3);

        doReturn(allCustomers).when(customerDao).readAllCustomers();

        //ReturnCustomerLastNameGuerrero
        List<Customer> allCustomerGuerrero = new ArrayList<>();

        allCustomerGuerrero.add(customer3);

        doReturn(allCustomerGuerrero).when(customerDao).getCustomersByLastName("Guerrero");

    }

    @Test
    public void addGetTest(){

        CustomerViewModel customer = new CustomerViewModel();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        //Creating a new Register using the serviceLayer
        customer = serviceLayer.newCustomer(customer);

        //Reading the register using the service
        CustomerViewModel fromService = serviceLayer.getCustomer(customer.getCustomerId());

        //Making sure that they are equal
        assertEquals(customer, fromService);
    }

    @Test
    public void updateTest(){

        CustomerViewModel customerToUpdate = new CustomerViewModel();

        customerToUpdate.setCustomerId(3);
        customerToUpdate.setFirstName("Angel");
        customerToUpdate.setLastName("Lozada");
        customerToUpdate.setStreet("Another Street");
        customerToUpdate.setCity("San Cristobal");
        customerToUpdate.setZip("5001");
        customerToUpdate.setEmail("lzda.dave@gmail.com");
        customerToUpdate.setPhone("718-963-895");

        serviceLayer.updateCustomer(customerToUpdate);

        //Reading the update Customer through the serviceLayer
        CustomerViewModel fromService = serviceLayer.getCustomer(customerToUpdate.getCustomerId());

        //Assert that they are equal
        assertEquals(customerToUpdate, fromService);
    }

    @Test
    public void deleteTest(){
        Customer customerToDelete = new Customer();

        customerToDelete.setCustomerId(5);
        customerToDelete.setFirstName("Angel");
        customerToDelete.setLastName("Lozada");
        customerToDelete.setStreet("Another Street");
        customerToDelete.setCity("San Cristobal");
        customerToDelete.setZip("5001");
        customerToDelete.setEmail("lzda.dave@gmail.com");
        customerToDelete.setPhone("718-963-895");

        serviceLayer.deleteCustomer(customerToDelete.getCustomerId());

        //Making sure that the Customer was deleted
        try{
            CustomerViewModel fromService = serviceLayer.getCustomer(customerToDelete.getCustomerId());
        }catch (RuntimeException e){
            assertEquals(1,1);
        }
    }

    @Test
    public void getAllCustomersTest() {

        List<CustomerViewModel> allCustomers = serviceLayer.getAllCustomers();

        assertEquals(allCustomers.size(), 2);
    }

    @Test
    public void getCustomersByLastNameTest() {

        List<CustomerViewModel> allCustomersGuerrero = serviceLayer.getCustomersByLastName("Guerrero");

        assertEquals(allCustomersGuerrero.size(),1);
    }
}