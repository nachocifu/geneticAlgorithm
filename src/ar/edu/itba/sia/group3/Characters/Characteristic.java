package ar.edu.itba.sia.group3.Characters;

import java.util.Objects;

public class Characteristic {
    public final long id;
    public final double experience, strength, life, resistance, agility, height;

    private Characteristic(double experience, double strength, double life, double resistance, double agility, long id, double height) {
        this.experience = experience;
        this.strength = strength;
        this.life = life;
        this.resistance = resistance;
        this.agility = agility;
        this.id = id;
        this.height = height;
    }

    /**
     * Used for armour
     *
     * @param experience
     * @param strength
     * @param life
     * @param resistance
     * @param agility
     * @param id
     */
    public Characteristic(double experience, double strength, double life, double resistance, double agility, long id) {
        this(experience, strength, life, resistance, agility, id, 0);
    }

    /**
     * Used for height
     * @param height
     */
    public Characteristic(double height) {
        this(0,0,0,0,0,0,height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Characteristic that = (Characteristic) o;
        return id == that.id &&
                Double.compare(that.experience, experience) == 0 &&
                Double.compare(that.strength, strength) == 0 &&
                Double.compare(that.life, life) == 0 &&
                Double.compare(that.resistance, resistance) == 0 &&
                Double.compare(that.agility, agility) == 0 &&
                Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, experience, strength, life, resistance, agility, height);
    }
}
