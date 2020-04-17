package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

public interface Breeder<E extends Victim> {
    List<E> breed(List<VictimPairs<E>> pairs);
}
