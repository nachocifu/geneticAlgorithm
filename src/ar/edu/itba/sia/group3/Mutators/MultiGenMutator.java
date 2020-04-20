package ar.edu.itba.sia.group3.Mutators;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;

import java.util.*;

public class MultiGenMutator {


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

        //select allele range
        int allele;
        int randomAlleleAmmount = rand.nextInt(character.getAllelesAmmount() + 1); //nextInt is exclusive
        Set<Integer> mutatedAlleles = new HashSet<>();
        CharacteristicType toMutate;
        Character newCharacter = new Character(character.getCharacterAlleles());
        int i = 0;
        while (i < randomAlleleAmmount){
            allele = rand.nextInt(character.getAllelesAmmount() + 1);

            if(!mutatedAlleles.contains(allele)){
                i++;
                toMutate = CharacteristicType.values()[allele];
                mutatedAlleles.add(allele);
                if(toMutate == CharacteristicType.HEIGHT){
                    double newHeight = rand.nextDouble()*(2 - 1.3) + 1.3;
                    newCharacter.getCharacterAlleles().put(CharacteristicType.HEIGHT,new Characteristic(newHeight));
                }
                else {
                    newCharacter.getCharacterAlleles().put(toMutate,Characteristic.getRandomCharacteristic(toMutate));
                }
            }
        }

        return newCharacter;
    }
}
