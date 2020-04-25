package ar.edu.itba.sia.group3.Characters;

import java.util.Map;

public class Spy extends Character {

    public Spy(Characteristic helmet, Characteristic weapon, Characteristic chestplate, Characteristic gauntlets, Characteristic boots, Characteristic height) {
        super(helmet, weapon, chestplate, gauntlets, boots, height);
    }

    public Spy(Map<CharacteristicType, Characteristic> characteristics) {
        super(characteristics);
    }

    @Override
    public double getFitness() {
        return 0.8*getAttack()+0.3*getDefense();
    }
}
