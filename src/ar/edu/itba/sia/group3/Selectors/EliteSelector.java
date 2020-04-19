package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EliteSelector implements Selector<Character> {

    private final int k;

    public EliteSelector(int k) {
        this.k = k;
    }


    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {
        Collections.sort(currentGeneration, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return (int) (-(o2.getFitness()-o1.getFitness()));
            }
        });

        if(k>currentGeneration.size())
            throw new Exception("No puedo llegar a un caso con K mayor que la generacion que me llegue");

        return currentGeneration.subList(0, k);
    }

}
