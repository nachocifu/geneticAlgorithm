package ar.edu.itba.sia.group3.umbrellaCorporation;

public class VictimPairs<V extends Victim> {
    protected V a,b;

    public VictimPairs(V a, V b) {
        this.a = a;
        this.b = b;
    }
}
