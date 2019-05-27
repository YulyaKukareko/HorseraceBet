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
    private String email;
    private String firstName;
    private String lastName;
    private long countryId;
    private BigDecimal balance;
    private String password;
    private Role role;

    public User() {
    }

    public User(long id, String email, String firstName, String lastName, long countryId, BigDecimal balance,
                String password, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryId = countryId;
        this.balance = balance;
        this.password = password;
        this.role = role;
    }

    /**
     * @return user id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return email user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return country id
     */
    public long getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     */
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    /**
     * @return user balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return user role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                countryId == user.countryId &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, countryId, balance, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", countryId=" + countryId +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
