package com.company.retailapiservice.viewmodel;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class LevelUpViewModel {

    private int levelUpId;

    @NotNull(message = "Please supply a value for Customer ID")
    private int customerId;

    @NotNull(message = "Please supply a value. Add '0' for purchases not eligible for points")
    private int points;

    @Future(message = "Cannot be a past date")
    @NotNull(message = "Please provide a membership start date")
    private LocalDate memberDate;

    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUpViewModel that = (LevelUpViewModel) o;
        return getLevelUpId() == that.getLevelUpId() &&
                getCustomerId() == that.getCustomerId() &&
                getPoints() == that.getPoints() &&
                getMemberDate().equals(that.getMemberDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelUpId(), getCustomerId(), getPoints(), getMemberDate());
    }

}
