package com.company.customerservice.dao;

import com.company.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoJdbcTemplateImplTest {

    @Autowired
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = customerDao.readAllCustomers();

        customerList.stream().forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));
    }

    @Test
    public void createReadDeleteTest(){

        Customer customer = new Customer();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        //creating a new Customer Register in the database
        customer = customerDao.createCustomer(customer);

        //Reading it from the databse
        Customer fromDatabase = customerDao.readCustomer(customer.getCustomerId());

        //Making sure that they are equal
        assertEquals(customer, fromDatabase);

        //Deleting it from the database
        customerDao.deleteCustomer(customer.getCustomerId());

        //Reading it from the database
        fromDatabase = customerDao.readCustomer(customer.getCustomerId());

        assertNull(fromDatabase);

    }
    @Test
    public void readAllCustomersTest() {

        Customer customer = new Customer();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        customerDao.createCustomer(customer);

        //Creating another Customer registry in the databse
        Customer customer2 = new Customer();

        customer2.setFirstName("Felipe");
        customer2.setLastName("Martinez");
        customer2.setStreet("Las Nutrias");
        customer2.setCity("San Cristobal");
        customer2.setZip("5001");
        customer2.setEmail("pipe@gmail.com");
        customer2.setPhone("719-963-458");

        customerDao.createCustomer(customer2);

        //Getting all the customer from the database
        List<Customer> customerList = customerDao.readAllCustomers();

        //Making sure that the 2 previous created registers were retrieved
        assertEquals(customerList.size(),2);
    }

    @Test
    public void updateCustomerTest() {

        Customer customer = new Customer();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        customer = customerDao.createCustomer(customer);

        customer.setEmail("THISEMAILHAVECHANGED@gmail.com");

        //updating the Customer register in the database
        customerDao.updateCustomer(customer);

        //Reading the register from the database
        Customer fromDatabase = customerDao.readCustomer(customer.getCustomerId());

        //Assert that the changes made by the update took effect

        assertEquals(customer, fromDatabase);
    }

    @Test
    public void getCustomersByLastNameTest() {

        Customer customer = new Customer();

        customer.setFirstName("Angel");
        customer.setLastName("Lozada");
        customer.setStreet("Los Naranjos");
        customer.setCity("San Cristobal");
        customer.setZip("5001");
        customer.setEmail("lzda.dave@gmail.com");
        customer.setPhone("718-963-895");

        customerDao.createCustomer(customer);

        //Creating another Customer registry in the databse
        Customer customer2 = new Customer();

        customer2.setFirstName("Felipe");
        customer2.setLastName("Martinez");
        customer2.setStreet("Las Nutrias");
        customer2.setCity("San Cristobal");
        customer2.setZip("5001");
        customer2.setEmail("pipe@gmail.com");
        customer2.setPhone("719-963-458");

        customerDao.createCustomer(customer2);

        //Reading the Customer registers with the property Martinez in the lastName
        List<Customer> customerMartinez = customerDao.getCustomersByLastName("Martinez");

        //Asserting that one register is retrieved
        assertEquals(customerMartinez.size(), 1);
    }
}