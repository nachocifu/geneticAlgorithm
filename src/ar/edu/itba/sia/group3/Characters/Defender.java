package ar.edu.itba.sia.group3.Characters;

import java.util.Map;

public class Defender extends Character {

    public Defender(Characteristic helmet, Characteristic weapon, Characteristic chestplate, Characteristic gauntlets, Characteristic boots, Characteristic height) {
        super(helmet, weapon, chestplate, gauntlets, boots, height);
    }

    public Defender(Map<CharacteristicType, Characteristic> characteristics) {
        super(characteristics);
    }

    @Override
    public double getFitness() {
        return 0.3*getAttack()+0.8*getDefense();
    }
}
