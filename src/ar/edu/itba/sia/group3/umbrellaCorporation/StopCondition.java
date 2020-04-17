package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

/**
 * Receives a generation and decides if goal has been achieved
 */
public interface StopCondition<E extends Victim> {
    Boolean shouldContinue(List<E> currentGeneration);
}
