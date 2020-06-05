package com.electricity.model.user;

import com.electricity.enumeration.repo.TableName;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String tableName;

    private volatile BigDecimal currentFundsAmount;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        this.currentFundsAmount = BigDecimal.valueOf(200_000_000);
        this.tableName = TableName.POWER_PLANT.getName() + "_" + login;
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

    public BigDecimal getCurrentFundsAmount() {
        return currentFundsAmount;
    }

    public synchronized void setCurrentFundsAmount(BigDecimal currentFundsAmount) {
        this.currentFundsAmount = currentFundsAmount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", currentFundsAmount=" + currentFundsAmount +
                '}';
    }
}