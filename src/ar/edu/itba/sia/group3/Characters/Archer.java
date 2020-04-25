package ar.edu.itba.sia.group3.Characters;

import java.util.Map;

public class Archer extends Character {

    public Archer(Characteristic helmet, Characteristic weapon, Characteristic chestplate, Characteristic gauntlets, Characteristic boots, Characteristic height) {
        super(helmet, weapon, chestplate, gauntlets, boots, height);
    }

    public Archer(Map<CharacteristicType, Characteristic> characteristics) {
        super(characteristics);
    }

    @Override
    public double getFitness() {
        return 0.9*getAttack()+0.1*getDefense();
    }
}
