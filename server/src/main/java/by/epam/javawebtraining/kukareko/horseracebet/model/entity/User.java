package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class User implements Serializable {

    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private String country;
    private BigDecimal balance;
    private String password;
    private Role role;

    public User() {
    }

    public User(long id, String login, String firstName, String lastName, String country, BigDecimal balance,
                String password, Role role) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.balance = balance;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                role == user.role &&
                Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(country, user.country) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, firstName, lastName, country, balance, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
