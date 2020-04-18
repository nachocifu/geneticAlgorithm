package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

/**
 * Given a population, returns its selection
 * TODO
 */
public interface Selector<E extends Victim> {

    List<E> select(List<E> currentGeneration);
}
