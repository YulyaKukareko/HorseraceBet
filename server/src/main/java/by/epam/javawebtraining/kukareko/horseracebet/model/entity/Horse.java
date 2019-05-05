package by.epam.javawebtraining.kukareko.horseracebet.model.entity;

import java.util.Objects;

/**
 * @author Yulya Kukareko
 * @version 1.0 10 Apr 2019
 */
public class Horse {

    private long id;
    private String trainer;
    private String jockey;
    private float weight;
    private String name;

    public Horse() {
    }

    public Horse(long id, String trainer, String jockey, float weight, String name) {
        this.id = id;
        this.trainer = trainer;
        this.jockey = jockey;
        this.weight = weight;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return id == horse.id &&
                Float.compare(horse.weight, weight) == 0 &&
                Objects.equals(trainer, horse.trainer) &&
                Objects.equals(jockey, horse.jockey) &&
                Objects.equals(name, horse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainer, jockey, weight, name);
    }

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                ", trainer='" + trainer + '\'' +
                ", jockey='" + jockey + '\'' +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }
}
