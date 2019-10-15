package com.company.adminapiservice.viewmodel;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class LevelUpViewModel {

    private int levelUpId;

    @NotNull(message = "Please supply a customerId")
    @Min(value = 1, message = "customerId must be a positive integer number")
    private Integer customerId;

    private Integer points;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a purchase date for the account creation")
    private LocalDate memberDate;

    //getters and setters
    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUpViewModel that = (LevelUpViewModel) o;
        return getLevelUpId() == that.getLevelUpId() &&
                Objects.equals(getCustomerId(), that.getCustomerId()) &&
                Objects.equals(getPoints(), that.getPoints()) &&
                Objects.equals(getMemberDate(), that.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelUpId(), getCustomerId(), getPoints(), getMemberDate());
    }
}
