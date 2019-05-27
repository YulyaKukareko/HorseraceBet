package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class Bet {

    private long id;
    private BetType type;
    private long firstStartingPriceHorseId;
    private long secondStartingPriceHorseId;
    private float coefficient;

    public Bet() {
    }

    public Bet(long id, BetType type, long firstStartingPriceHorseId, long secondStartingPriceHorseId, float coefficient) {
        this.id = id;
        this.type = type;
        this.firstStartingPriceHorseId = firstStartingPriceHorseId;
        this.secondStartingPriceHorseId = secondStartingPriceHorseId;
        this.coefficient = coefficient;
    }

    /**
     * @return bet id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id bet
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return bet type
     */
    public BetType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(BetType type) {
        this.type = type;
    }

    /**
     * @return first starting price horse id of bet
     */
    public long getFirstStartingPriceHorseId() {
        return firstStartingPriceHorseId;
    }

    /**
     * @param firstStartingPriceHorseId
     */
    public void setFirstStartingPriceHorseId(long firstStartingPriceHorseId) {
        this.firstStartingPriceHorseId = firstStartingPriceHorseId;
    }

    /**
     * @return second starting price horse id of bet
     */
    public long getSecondStartingPriceHorseId() {
        return secondStartingPriceHorseId;
    }

    /**
     * @param secondStartingPriceHorseId
     */
    public void setSecondStartingPriceHorseId(long secondStartingPriceHorseId) {
        this.secondStartingPriceHorseId = secondStartingPriceHorseId;
    }

    /**
     * @return bet coefficient
     */
    public float getCoefficient() {
        return coefficient;
    }

    /**
     * @param coefficient bet
     */
    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return id == bet.id &&
                firstStartingPriceHorseId == bet.firstStartingPriceHorseId &&
                secondStartingPriceHorseId == bet.secondStartingPriceHorseId &&
                Float.compare(bet.coefficient, coefficient) == 0 &&
                Objects.equals(type, bet.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, firstStartingPriceHorseId, secondStartingPriceHorseId, coefficient);
    }
}
