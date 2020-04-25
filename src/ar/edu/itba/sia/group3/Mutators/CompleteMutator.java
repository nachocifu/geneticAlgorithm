package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class CompleteMutator implements Mutator<Character> {
    private double mutationProbability;
    private Random rand;

    public CompleteMutator(double mutationProbability) {
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
        if (rand.nextDouble() > mutationProbability) {
            return character;
        }
        Map<CharacteristicType,Characteristic> newSet = Characteristic.getRandomSet();

        for(Map.Entry<CharacteristicType,Characteristic> item : newSet.entrySet()){
            character.getCharacterAlleles().put(item.getKey(),item.getValue());
        }
        return character;
    }
}

