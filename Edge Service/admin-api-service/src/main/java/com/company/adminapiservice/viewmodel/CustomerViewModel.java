package com.company.adminapiservice.viewmodel;

import javax.validation.constraints.*;
import java.util.Objects;

public class CustomerViewModel {

    private int customerId;

    @NotBlank(message = "firstName is required!")
    @Size(max = 50, message = "firstName can not be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "lastName is required!")
    @Size(max = 50, message = "lastName can not be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "street is required!")
    @Size(max = 50, message = "street can not be longer than 50 characters")
    private String street;

    @NotBlank(message = "city is required!")
    @Size(max = 50, message = "city can not be longer than 50 characters")
    private String city;

    @NotBlank(message = "zipcode is required!")
    @Size(min = 5, max = 5, message = "zipcode must be 5 integer positive numbers")
    @Pattern(regexp = "^[0-9]*$", message = "zipcode must be 5 integer positive numbers")
    private String zip;

    @NotBlank(message = "email is required!")
    @Size(max = 75, message = "email can not be longer than 75 characters")
    @Email
    private String email;

    @NotBlank(message = "phone is required!")
    @Size(max = 20, message = "phone can not be longer than 20 characters")
    private String phone;

    //getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerViewModel that = (CustomerViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getStreet().equals(that.getStreet()) &&
                getCity().equals(that.getCity()) &&
                getZip().equals(that.getZip()) &&
                getEmail().equals(that.getEmail()) &&
                getPhone().equals(that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getFirstName(), getLastName(), getStreet(), getCity(), getZip(), getEmail(), getPhone());
    }
}
