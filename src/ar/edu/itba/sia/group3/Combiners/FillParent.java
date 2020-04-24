package ar.edu.itba.sia.group3.Combiners;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Combiner;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.LinkedList;
import java.util.List;

public class FillParent implements Combiner<Character> {

    public final int n;
    public final Selector<Character> selector;

    /**
     * The selectors K value should be N if K>N or N-K if N>K
     * @param n
     * @param selector
     */
    public FillParent(int n, Selector<Character> selector) {
        this.n = n;
        this.selector = selector;
    }

    @Override
    public List<Character> combine(List<Character> currentGeneration, List<Character> children) throws Exception {

        if(children.size()>=n)
            return selector.select(children);

        List<Character> mergedList = new LinkedList<>(children);
        mergedList.addAll(selector.select(currentGeneration));
        return mergedList;

    }
}
