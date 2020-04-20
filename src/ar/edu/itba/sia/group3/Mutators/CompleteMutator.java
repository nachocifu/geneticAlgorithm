package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Mutator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CompleteMutator implements Mutator<Character> {
    private double mutationProbability;
    private Random rand;

    public MultiGenMutator(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        this.rand = new Random();
    }


    @Override
    public List<Character> mutate(List<Character> zombies) {
        List<Character> mutated = new LinkedList<Character>();
        for (Character character : zombies) {
            mutated.add(mutateCharacter(character));
        }
        return mutated;
    }

    public Character mutateCharacter(Character character) {
        if (rand.nextDouble() > mutationProbability) {
            return character;
        }
        Map<CharacteristicType,Characteristic> newSet = Characteristic.getRandomSet();
        return new Character(newSet);
    }
}

