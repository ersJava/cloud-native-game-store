package com.company.adminapiservice.viewmodel;

import java.util.Objects;

public class CustomerViewModel {

    private int customerId;

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;

    private String creationDate;

    private LevelUpViewModel levelUpAccount;

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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public LevelUpViewModel getLevelUpAccount() {
        return levelUpAccount;
    }

    public void setLevelUpAccount(LevelUpViewModel levelUpAccount) {
        this.levelUpAccount = levelUpAccount;
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
                getPhone().equals(that.getPhone()) &&
                getCreationDate().equals(that.getCreationDate()) &&
                getLevelUpAccount().equals(that.getLevelUpAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getFirstName(), getLastName(), getStreet(), getCity(), getZip(), getEmail(), getPhone(), getCreationDate(), getLevelUpAccount());
    }
}
