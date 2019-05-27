package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class Result {

    private long id;
    private long raceId;
    private int place;
    private long horseId;

    public Result() {
    }

    public Result(long id, long raceId, int place, long horseId) {
        this.id = id;
        this.raceId = raceId;
        this.place = place;
        this.horseId = horseId;
    }

    /**
     * @return id result
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
     * @return race id of
     */
    public long getRaceId() {
        return raceId;
    }

    /**
     * @param raceId
     */
    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    /**
     * @return horse id
     */
    public long getHorseId() {
        return horseId;
    }

    /**
     * @param horseId
     */
    public void setHorseId(long horseId) {
        this.horseId = horseId;
    }

    /**
     * @return result place
     */
    public int getPlace() {
        return place;
    }

    /**
     * @param place
     */
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id &&
                raceId == result.raceId &&
                place == result.place &&
                horseId == result.horseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, raceId, place, horseId);
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", raceId=" + raceId +
                ", place=" + place +
                ", horseId=" + horseId +
                '}';
    }
}
