package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TorneoDeterministico implements Selector<Character> {

    private final int k,m;

    public TorneoDeterministico(int k, int m) {
        this.k = k;
        this.m = m;
    }


    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {

        List<Character> selectedCharacters = new LinkedList<Character>();
        List<Character> batch;
        Random random = new Random();

        for (int index =0; index<k;index++){

            // create group of M random characters
            batch = new LinkedList<>();
            for(int batchIndex = 0 ; batchIndex<m ; batchIndex++) {
                batch.add(
                        currentGeneration.get(
                                random.nextInt(currentGeneration.size())
                        )
                );
            }
            // keep the best one
            Optional<Character> optional = batch.parallelStream().max(Comparator.comparing(Character::getFitness));
            if (optional.isPresent())
                selectedCharacters.add(optional.get());
            else
                throw new Exception("Failed retrieving max element on batch. This should never happen.");

        }

        return selectedCharacters.parallelStream().map(
                character -> {
                    try {
                        return character.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
    }

}
