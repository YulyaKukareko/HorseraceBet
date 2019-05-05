package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class UserBet {

    private long id;
    private long betId;
    private long userId;
    private boolean haveSp;
    private BigDecimal betMoney;
    private String status;
    private float coefficient;

    public UserBet() {
    }

    public UserBet(long id, long betId, long userId, boolean haveSp, BigDecimal betMoney, String status, float coefficient) {
        this.id = id;
        this.betId = betId;
        this.userId = userId;
        this.haveSp = haveSp;
        this.betMoney = betMoney;
        this.status = status;
        this.coefficient = coefficient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isHaveSp() {
        return haveSp;
    }

    public void setHaveSp(boolean haveSp) {
        this.haveSp = haveSp;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBet userBet = (UserBet) o;
        return id == userBet.id &&
                betId == userBet.betId &&
                userId == userBet.userId &&
                haveSp == userBet.haveSp &&
                Float.compare(userBet.coefficient, coefficient) == 0 &&
                Objects.equals(betMoney, userBet.betMoney) &&
                Objects.equals(status, userBet.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, betId, userId, haveSp, betMoney, status, coefficient);
    }

    @Override
    public String toString() {
        return "UserBet{" +
                "id=" + id +
                ", betId=" + betId +
                ", userId=" + userId +
                ", haveSp=" + haveSp +
                ", betMoney=" + betMoney +
                ", status='" + status + '\'' +
                ", coefficient=" + coefficient +
                '}';
    }
}
