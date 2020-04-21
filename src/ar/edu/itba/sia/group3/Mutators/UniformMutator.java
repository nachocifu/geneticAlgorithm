package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class UniformMutator<C extends Character> implements Mutator<C> {
    private double mutationProbability;
    private Random rand;

    public UniformMutator(double mutationProbability) {
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
        CharacteristicType toMutate;
        C newCharacter = (C) character.clone(); // cero seguro de esto
        for(int i = 0;i < character.getAllelesAmmount();i++){
            if(rand.nextDouble() <= mutationProbability){
                toMutate = CharacteristicType.values()[i];
                newCharacter.getCharacterAlleles().put(toMutate, Characteristic.getRandomCharacteristic(toMutate));
            }
        }
        return newCharacter;
    }
}
