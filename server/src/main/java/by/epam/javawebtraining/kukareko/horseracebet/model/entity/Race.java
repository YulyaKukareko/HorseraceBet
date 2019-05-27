package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class Race {

    private long id;
    private String name;
    private long countryId;
    private double distance;
    private BigDecimal purse;
    private RaceType type;
    private Timestamp time;

    public Race() {
    }

    public Race(long id, String name, long countryId, double distance, BigDecimal purse, RaceType type, Timestamp time) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.distance = distance;
        this.purse = purse;
        this.type = type;
        this.time = time;
    }

    /**
     * @return race id
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
     * @return race name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return country id of current race
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
     * @return distance of race
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return purse of race
     */
    public BigDecimal getPurse() {
        return purse;
    }

    /**
     * @param purse
     */
    public void setPurse(BigDecimal purse) {
        this.purse = purse;
    }

    /**
     * @return race type of race
     */
    public RaceType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(RaceType type) {
        this.type = type;
    }

    /**
     * @return time of race
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return id == race.id &&
                countryId == race.countryId &&
                Double.compare(race.distance, distance) == 0 &&
                Objects.equals(name, race.name) &&
                Objects.equals(purse, race.purse) &&
                type == race.type &&
                Objects.equals(time, race.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryId, distance, purse, type, time);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", distance=" + distance +
                ", purse=" + purse +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}
