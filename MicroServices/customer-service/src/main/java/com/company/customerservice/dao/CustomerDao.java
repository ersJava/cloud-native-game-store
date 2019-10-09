package com.company.customerservice.dao;

import com.company.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {

    //Create
    Customer createCustomer(Customer customer);

    //Read
    Customer readCustomer(int customerId);

    List<Customer> readAllCustomers();

    //Update
    void updateCustomer(Customer customer);

    //Delete
    void deleteCustomer(int customerId);

    //ReadCustomersByLastName
    List<Customer> getCustomersByLastName(String lastName);
}
