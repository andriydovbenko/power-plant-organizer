package com.electricity.model.user;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class User {

    private String id;
    private String firstName;
    private String lastName;

    private volatile BigDecimal currentAmountOfFunds;

    public User(String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.currentAmountOfFunds = BigDecimal.valueOf(200_000_000);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public synchronized BigDecimal getCurrentAmountOfFunds() {
        return currentAmountOfFunds;
    }

    public synchronized void setCurrentAmountOfFunds(BigDecimal currentAmountOfFunds) {
        this.currentAmountOfFunds = currentAmountOfFunds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currentAmountOfFunds=" + currentAmountOfFunds +
                '}';
    }
}