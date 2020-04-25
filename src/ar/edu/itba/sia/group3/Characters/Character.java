package ar.edu.itba.sia.group3.Characters;

import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Character extends Victim implements Cloneable {

    // The Alleles of this character
    protected Map<CharacteristicType, Characteristic> alleles;

    public Character clone() throws CloneNotSupportedException{
        Character newCharacter = (Character) super.clone();
        newCharacter.alleles = new HashMap<>(alleles);
        return newCharacter;
    }

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
    
    private Double getATM(){
        Double h = alleles.get(CharacteristicType.HEIGHT).height;
        return 0.7-Math.pow(3*h-5, 4)+Math.pow(3*h-5,2)+(2*h/10);
    }

    private Double getDEM(){
        Double h = alleles.get(CharacteristicType.HEIGHT).height;
        return 1.9+Math.pow(2.5*h-4.16,4)-Math.pow(2.5*h-4.16,2)-(3*h/10);
    }

    private Double getStrength(){
        Double sum = 0.0;
        for (CharacteristicType type: CharacteristicType.values()) {
            if(type == CharacteristicType.HEIGHT) continue;
            sum += alleles.get(type).strength;
        }
        return 100*Math.tanh(0.01*sum);
    }

    private Double getAgility(){
        Double sum = 0.0;
        for (CharacteristicType type: CharacteristicType.values()) {
            if(type == CharacteristicType.HEIGHT) continue;
            sum += alleles.get(type).agility;
        }
        return Math.tanh(0.01*sum);
    }

    /**
     * Pericia
     * @return
     */
    private Double getExperience(){
        Double sum = 0.0;
        for (CharacteristicType type: CharacteristicType.values()) {
            if(type == CharacteristicType.HEIGHT) continue;
            sum += alleles.get(type).experience;
        }
        return 0.6*Math.tanh(0.01*sum);
    }

    private Double getResistance(){
        Double sum = 0.0;
        for (CharacteristicType type: CharacteristicType.values()) {
            if(type == CharacteristicType.HEIGHT) continue;
            sum += alleles.get(type).resistance;
        }
        return Math.tanh(0.01*sum);
    }

    private Double getLife(){
        Double sum = 0.0;
        for (CharacteristicType type: CharacteristicType.values()) {
            if(type == CharacteristicType.HEIGHT) continue;
            sum += alleles.get(type).life;
        }
        return 100*Math.tanh(0.01*sum);
    }

    public Double getAttack(){
        return (getAgility()+getExperience())*getStrength()*getATM();
    }

    public Double getDefense(){
        return (getResistance()+getExperience())*getLife()*getDEM();
    }

    public static String getSummary(List<Character> list) {
        double fitness, sum=0;
        double max = list.get(0).getFitness();
        double min = max;

        for (Character character: list) {
            fitness = character.getFitness();
            sum+=fitness;
            if(fitness<min) min=fitness;
            if(max<fitness) max=fitness;
        }

        return "Max "+max+" Min "+min+" Sum "+sum+" AVG "+(sum/list.size());
    }

}
