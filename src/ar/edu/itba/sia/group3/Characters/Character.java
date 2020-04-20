package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class Character extends Victim {

    // The Alleles of this character
    protected Map<CharacteristicType, Characteristic> alleles;

    public int getAllelesAmmount(){
        return alleles.size();
    }

    public Map<CharacteristicType,Characteristic> getCharacterAlleles(){
        return this.alleles;
    }

    public Characteristic getHelmet(){
        return this.alleles.get(CharacteristicType.HELMET);
    }

    public Characteristic getGauntlets(){
        return this.alleles.get(CharacteristicType.GAUNTLETS);
    }

    public Characteristic getchestPlate(){
        return this.alleles.get(CharacteristicType.CHESTPLATE);
    }

    public Characteristic getBoots(){
        return this.alleles.get(CharacteristicType.BOOTS);
    }

    public Characteristic getWeapon(){
        return this.alleles.get(CharacteristicType.WEAPON);
    }

    public Characteristic getHeight(){
        return this.alleles.get(CharacteristicType.HEIGHT);
    }

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

    public Character(Map<CharacteristicType,Characteristic> characteristics){
        alleles = new HashMap<>();
        for(Map.Entry<CharacteristicType,Characteristic> ch : characteristics.entrySet()){
            alleles.put(ch.getKey(),ch.getValue());
        }
    }


}
