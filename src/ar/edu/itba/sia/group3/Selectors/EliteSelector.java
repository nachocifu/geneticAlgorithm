package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EliteSelector implements Selector<Character> {

    private final int k;

    public EliteSelector(int k) {
        this.k = k;
    }


    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {
        currentGeneration = currentGeneration.parallelStream()
                .sorted(Comparator.comparing(Character::getFitness).reversed())
                .collect(Collectors.toList());

        if(k>currentGeneration.size())
            throw new Exception("No puedo llegar a un caso con K mayor que la generacion que me llegue");

        return currentGeneration.subList(0, k).parallelStream().map(
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
