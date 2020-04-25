package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SingleGenMutator implements Mutator<Character> {


    private double mutationProbability;
    private Random rand;



    public SingleGenMutator(double mutationProbability){
        this.mutationProbability = mutationProbability;
        this.rand = new Random();
    }


    @Override
    public List<Character> mutate(List<Character> zombies) {
        return zombies.parallelStream()
                .map(zombie -> mutateCharacter(zombie))
                .collect(Collectors.toList());
    }

    private Character mutateCharacter(Character character) {
        if(rand.nextDouble() > mutationProbability){
            return character;
        }

        //select allele
        int randomAllele = rand.nextInt(character.getAllelesAmmount()+1); //nextInt is exclusive
        CharacteristicType toMutate = CharacteristicType.values()[randomAllele];
        if(toMutate == CharacteristicType.HEIGHT){
            double newHeight = rand.nextDouble()*(2 - 1.3) + 1.3;
            character.getCharacterAlleles().put(CharacteristicType.HEIGHT,new Characteristic(newHeight));
        }
        else {
            character.getCharacterAlleles().put(toMutate,Characteristic.getRandomCharacteristic(toMutate));
        }
        return character;
    }



}
