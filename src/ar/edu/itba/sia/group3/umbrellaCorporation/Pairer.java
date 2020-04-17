package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

public interface Pairer<V extends Victim> {
    List<VictimPairs<V>> getPairs(List<V> zombies);
}
