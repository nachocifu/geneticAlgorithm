package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CompleteMutator<C extends Character> implements Mutator<C> {
    private double mutationProbability;
    private Random rand;

    public CompleteMutator(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        this.rand = new Random();
    }


    @Override
    public List<C> mutate(List<C> zombies) {
        List<C> mutated = new LinkedList<>();
        for (C character : zombies) {
            try {
                mutated.add(mutateCharacter(character));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return mutated;
    }

    private C mutateCharacter(C character) throws CloneNotSupportedException {
        if (rand.nextDouble() > mutationProbability) {
            return character;
        }
        Map<CharacteristicType,Characteristic> newSet = Characteristic.getRandomSet();
        C newCharacter = (C) character.clone(); //ah?
        for(Map.Entry<CharacteristicType,Characteristic> item : newSet.entrySet()){
            newCharacter.getCharacterAlleles().put(item.getKey(),item.getValue());
        }
        return newCharacter;
    }
}

