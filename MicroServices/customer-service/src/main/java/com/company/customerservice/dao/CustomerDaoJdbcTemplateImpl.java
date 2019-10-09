package com.company.customerservice.dao;

import com.company.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    //Prepared Statements
    public static final String INSERT_CUSTOMER =
            "insert into customer (first_name, last_name, street, city, zip, email, phone) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_CUSTOMER_BY_ID =
            "select * from customer where customer_id = ?";

    public static final String SELECT_ALL_CUSTOMERS=
            "select * from customer";

    public static final String SELECT_ALL_CUSTOMERS_BY_LAST_NAME=
            "select * from customer where last_name = ?";

    public static final String UPDATE_CUSTOMER =
            "update customer set first_name = ?, last_name = ?, street = ?, city = ?, zip = ?, email = ?, phone = ? where customer_id = ?";

    public static final String DELETE_CUSTOMER =
            "delete from customer where customer_id = ?";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //ServiceLayer method newCustomer
    @Override
    public Customer createCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone()
                );
        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        customer.setCustomerId(id);

        return customer;
    }

    //ServiceLayer method getCustomer
    @Override
    public Customer readCustomer(int customerId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID, this::mapRowToCustomer,customerId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //ServiceLayer method getAllCustomers
    @Override
    public List<Customer> readAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS,this::mapRowToCustomer);
    }

    //ServiceLayer method updateCustomer
    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerId()
                );
    }

    //ServiceLayer method deleteCustomer
    @Override
    public void deleteCustomer(int customerId) {
        jdbcTemplate.update(DELETE_CUSTOMER, customerId);
    }

    //ServiceLayer method getCustomersByLastName
    @Override
    public List<Customer> getCustomersByLastName(String lastName) {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS_BY_LAST_NAME,this::mapRowToCustomer, lastName);
    }

    //Helper method
    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {

        Customer customer = new Customer();

        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setStreet(rs.getString("street"));
        customer.setCity(rs.getString("city"));
        customer.setZip(rs.getString("zip"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));

        return customer;
    }
}
