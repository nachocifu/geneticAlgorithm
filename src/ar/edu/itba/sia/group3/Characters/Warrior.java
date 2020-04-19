package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

public class Warrior extends Character {

    public Warrior(Characteristic helmet, Characteristic weapon, Characteristic chestplate, Characteristic gauntlets, Characteristic boots, Characteristic height) {
        super(helmet, weapon, chestplate, gauntlets, boots, height);
    }

    @Override
    public double getFitness() {
        return 0;
    }
}
