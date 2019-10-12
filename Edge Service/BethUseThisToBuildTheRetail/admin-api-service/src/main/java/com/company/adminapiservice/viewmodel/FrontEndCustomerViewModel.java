package com.company.adminapiservice.viewmodel;

import java.time.LocalDate;
import java.util.Objects;

public class FrontEndCustomerViewModel {

    private CustomerViewModel customerViewModel;

    private LocalDate creationDate;

    private LevelUpViewModel levelUpAccount;

    private boolean joinToLevelUp;

    //getters and setters
    public CustomerViewModel getCustomerViewModel() {
        return customerViewModel;
    }

    public void setCustomerViewModel(CustomerViewModel customerViewModel) {
        this.customerViewModel = customerViewModel;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LevelUpViewModel getLevelUpAccount() {
        return levelUpAccount;
    }

    public void setLevelUpAccount(LevelUpViewModel levelUpAccount) {
        this.levelUpAccount = levelUpAccount;
    }

    public boolean isJoinToLevelUp() {
        return joinToLevelUp;
    }

    public void setJoinToLevelUp(boolean joinToLevelUp) {
        this.joinToLevelUp = joinToLevelUp;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrontEndCustomerViewModel that = (FrontEndCustomerViewModel) o;
        return isJoinToLevelUp() == that.isJoinToLevelUp() &&
                Objects.equals(getCustomerViewModel(), that.getCustomerViewModel()) &&
                Objects.equals(getCreationDate(), that.getCreationDate()) &&
                Objects.equals(getLevelUpAccount(), that.getLevelUpAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerViewModel(), getCreationDate(), getLevelUpAccount(), isJoinToLevelUp());
    }
}
