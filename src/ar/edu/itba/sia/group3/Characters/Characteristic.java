package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Characteristic {
    public final long id;
    public final double experience, strength, life, resistance, agility, height;

    private static Map<CharacteristicType,Map<Integer,Characteristic>> characteristics;

    static {
        try {
            characteristics = Configuration.getCharacteristics();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Characteristic(double strength, double agility,double experience, double resistance,double life,double height,long id) {
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
    public Characteristic(double strength,double agility,double experience, double resistance, double life,long id) {
        this(strength,experience,agility,resistance,life,  0,id);
    }

    /**
     * Used for height
     * @param height
     */ // ID de altura es 0? eso no es para problemas?
    public Characteristic(double height) {
        this(0,0,0,0,0,height,0);
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

    public static Characteristic getRandomCharacteristic(CharacteristicType type){
        Random rand = new Random();
        //select a random item id from corresponding type
        int characteristicNumber = rand.nextInt(characteristics.get(type).size());
        //get item
        return characteristics.get(type).get(characteristicNumber);
    }

    public static Map<CharacteristicType,Characteristic> getRandomSet(){
        Map<CharacteristicType,Characteristic> randomSet = new HashMap<>();
        randomSet.put(CharacteristicType.HELMET,getRandomCharacteristic(CharacteristicType.HELMET));
        randomSet.put(CharacteristicType.GAUNTLETS,getRandomCharacteristic(CharacteristicType.GAUNTLETS));
        randomSet.put(CharacteristicType.BOOTS,getRandomCharacteristic(CharacteristicType.BOOTS));
        randomSet.put(CharacteristicType.WEAPON,getRandomCharacteristic(CharacteristicType.WEAPON));
        randomSet.put(CharacteristicType.CHESTPLATE,getRandomCharacteristic(CharacteristicType.CHESTPLATE));
        return randomSet;
    }
}
