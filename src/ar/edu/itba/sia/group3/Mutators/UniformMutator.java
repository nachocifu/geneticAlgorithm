package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UniformMutator implements Mutator<Character> {
    private double mutationProbability;
    private Random rand;

    public UniformMutator(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        this.rand = new Random();
    }


    @Override
    public List<Character> mutate(List<Character> zombies) {
        return zombies.parallelStream()
                .map(this::mutateCharacter)
                .collect(Collectors.toList());
    }

    private Character mutateCharacter(Character character) {
        CharacteristicType toMutate;

        for(int i = 0;i < character.getAllelesAmmount();i++){
            if(rand.nextDouble() <= mutationProbability){
                toMutate = CharacteristicType.values()[i];
                character.getCharacterAlleles().put(toMutate, Characteristic.getRandomCharacteristic(toMutate));
            }
        }
        return character;
    }
}
