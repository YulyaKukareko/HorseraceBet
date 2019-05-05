package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class HorseStartingPrice {

    private long id;
    private long raceId;
    private long horseId;
    private float sp;

    public HorseStartingPrice() {
    }

    public HorseStartingPrice(long id, long raceId, long horseId, float sp) {
        this.id = id;
        this.raceId = raceId;
        this.horseId = horseId;
        this.sp = sp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public long getHorseId() {
        return horseId;
    }

    public void setHorseId(long horseId) {
        this.horseId = horseId;
    }

    public float getSp() {
        return sp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorseStartingPrice that = (HorseStartingPrice) o;
        return id == that.id &&
                raceId == that.raceId &&
                horseId == that.horseId &&
                Float.compare(that.sp, sp) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, raceId, horseId, sp);
    }

    @Override
    public String toString() {
        return "HorseStartingPriceDAO{" +
                "id=" + id +
                ", raceId=" + raceId +
                ", horseId=" + horseId +
                ", sp=" + sp +
                '}';
    }
}