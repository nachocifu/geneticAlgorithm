package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Character extends Victim {

    // The Alleles of this character
    protected Map<CharacteristicType, Characteristic> alleles;

    public Character(Characteristic helmet, Characteristic weapon, Characteristic chestplate,
                     Characteristic gauntlets, Characteristic boots, Characteristic height) {
        alleles = new HashMap<>();
        alleles.put(CharacteristicType.HELMET, helmet);
        alleles.put(CharacteristicType.WEAPON, weapon);
        alleles.put(CharacteristicType.CHESTPLATE, chestplate);
        alleles.put(CharacteristicType.GAUNTLETS, gauntlets);
        alleles.put(CharacteristicType.BOOTS, boots);
        alleles.put(CharacteristicType.HEIGHT, height);
    }


}
