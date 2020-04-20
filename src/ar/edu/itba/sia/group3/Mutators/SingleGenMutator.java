package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SingleGenMutator implements Mutator<Character> {


    private double mutationProbability;
    private Random rand;



    public SingleGenMutator(double mutationProbability){
        this.mutationProbability = mutationProbability;
        this.rand = new Random();
    }


    @Override
    public List<Character> mutate(List<Character> zombies) {
        List<Character> mutated = new LinkedList<Character>();
        for(Character character : zombies){
            mutated.add(mutateCharacter(character));
        }
        return mutated;
    }

    public Character mutateCharacter(Character character){
        if(rand.nextDouble() > mutationProbability){
            return character;
        }
        //select allele
        int randomAllele = rand.nextInt(character.getAllelesAmmount()+1); //nextInt is exclusive
        CharacteristicType toMutate = CharacteristicType.values()[randomAllele];
        if(toMutate == CharacteristicType.HEIGHT){
            double newHeight = rand.nextDouble()*(2 - 1.3) + 1.3;
//            return new Character(character.getHelmet(),character.getWeapon(),character.getchestPlate(),character.getGauntlets(),character.getBoots(),new Characteristic(newHeight));
        }
        else {
//            Character newCharacter = new Character(character.getHelmet(),character.getWeapon(),character.getchestPlate(),character.getGauntlets(),character.getBoots(),character.getHeight());
//            newCharacter.getCharacterAlleles().put(toMutate,Characteristic.getRandomCharacteristic(toMutate));
        }
    }



}
