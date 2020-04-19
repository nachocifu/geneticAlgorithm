package ar.edu.itba.sia.group3.Combiners;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Combiner;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FillAll implements Combiner<Character> {

    public final int n;
    public final Selector<Character> selector;

    public FillAll(int n, Selector<Character> selector) {
        this.n = n;
        this.selector = selector;
    }

    @Override
    public List<Character> combine(List<Character> currentGeneration, List<Character> children) throws Exception {
        List<Character> mergedList = new LinkedList<>(currentGeneration);
        mergedList.addAll(children);

        return selector.select(mergedList);
    }
}
