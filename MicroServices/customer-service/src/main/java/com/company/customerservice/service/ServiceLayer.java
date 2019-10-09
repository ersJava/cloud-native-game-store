package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.exception.CustomerNotFoundException;
import com.company.customerservice.model.Customer;
import com.company.customerservice.viewmodel.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    CustomerDao customerDao;

    @Autowired
    public ServiceLayer(CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    //Create a new Customer
    @Transactional
    public CustomerViewModel newCustomer(CustomerViewModel cvm){

        return buildCustomerViewModel(customerDao.createCustomer(buildCustomer(cvm)));
    }

    //Read a Customer
    public CustomerViewModel getCustomer(int customerId){

      Customer customer = customerDao.readCustomer(customerId);

      if(customer == null){
          throw new CustomerNotFoundException("No Customer found in the database for id #" + customerId + " !");
      }else{
          return buildCustomerViewModel(customer);
      }
    }

    //Read all Customers
    public List<CustomerViewModel> getAllCustomers(){

        List<Customer> customerList = customerDao.readAllCustomers();

        if(customerList.size() == 0){
            throw new CustomerNotFoundException("The database is empty!!! No Customer(s) found in the Database");
        }else{
            return buildCvmList(customerList);
        }
    }

    //Read all Customer by lastName
    public List<CustomerViewModel> getCustomersByLastName(String lastName){

        List<Customer> customerList = customerDao.getCustomersByLastName(lastName);

        if(customerList.size() == 0){
            throw new CustomerNotFoundException("No Customer(s) found with the lastName: " + lastName + "!");
        }else{
            return buildCvmList(customerList);
        }
    }

    //Update a Customer
    public void updateCustomer(CustomerViewModel cvm){
        //Making sure that the Customer exist
        getCustomer(cvm.getCustomerId());

        customerDao.updateCustomer(buildCustomer(cvm));
    }

    //Delete a Customer
    public void deleteCustomer(int id){
        //getCustomer(id); //Remember to uncomment this

        customerDao.deleteCustomer(id);
    }

    //Helper methods
    List<CustomerViewModel> buildCvmList(List<Customer> customerList){
        List<CustomerViewModel> cvmList = new ArrayList<>();

        customerList.stream().forEach(customer -> cvmList.add(buildCustomerViewModel(customer)));

        return cvmList;
    }

    Customer buildCustomer(CustomerViewModel cvm){
        Customer customer = new Customer();

        if(cvm == null){
            return null;
        }else{
            customer.setCustomerId(cvm.getCustomerId());
            customer.setFirstName(cvm.getFirstName());
            customer.setLastName(cvm.getLastName());
            customer.setStreet(cvm.getStreet());
            customer.setCity(cvm.getCity());
            customer.setZip(cvm.getZip());
            customer.setEmail(cvm.getEmail());
            customer.setPhone(cvm.getPhone());
        }

        return customer;
    }

    CustomerViewModel buildCustomerViewModel(Customer customer){
        CustomerViewModel cvm = new CustomerViewModel();

        if(customer == null){
            return null;
        }else{
            cvm.setCustomerId(customer.getCustomerId());
            cvm.setFirstName(customer.getFirstName());
            cvm.setLastName(customer.getLastName());
            cvm.setStreet(customer.getStreet());
            cvm.setCity(customer.getCity());
            cvm.setZip(customer.getZip());
            cvm.setEmail(customer.getEmail());
            cvm.setPhone(customer.getPhone());
        }
        return  cvm;
    }
}
