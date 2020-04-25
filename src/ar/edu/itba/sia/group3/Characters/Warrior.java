package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.util.Map;

public class Warrior extends Character {

    public Warrior(Characteristic helmet, Characteristic weapon, Characteristic chestplate, Characteristic gauntlets, Characteristic boots, Characteristic height) {
        super(helmet, weapon, chestplate, gauntlets, boots, height);
    }

    public Warrior(Map<CharacteristicType, Characteristic> characteristics) {
        super(characteristics);
    }

    @Override
    public double getFitness() {
        return 0.6*getAttack()+0.6*getDefense();
    }
}
