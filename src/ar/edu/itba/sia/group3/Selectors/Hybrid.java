package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Hybrid implements Selector<Character> {

    public final Selector<Character> selectorA, selectorB;

    /**
     * Each selector should know how to select and how many it has to select
     * The how many part will be decided on setup and given as the K param to the selectors
     *
     * @param selectorA
     * @param selectorB
     */
    public Hybrid(Selector<Character> selectorA, Selector<Character> selectorB) {
        this.selectorA = selectorA;
        this.selectorB = selectorB;
    }

    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {
        List<Character> mergedList = new LinkedList<Character>(selectorA.select(currentGeneration));
        mergedList.addAll(selectorB.select(currentGeneration));
        return mergedList.parallelStream().map(
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
