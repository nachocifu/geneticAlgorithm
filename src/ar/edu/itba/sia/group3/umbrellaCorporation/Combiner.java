package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

/**
 * Receives old generation and new prospects, returns new population.
 */
public interface Combiner<E extends Victim> {
    List<E> combine(List<E> currentGeneration, List<E> zombies);
}
