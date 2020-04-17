package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

public interface Mutator<E extends Victim> {
    List<E> mutate(List<E> zombies);
}
