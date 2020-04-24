package ar.edu.itba.sia.group3.Pairers;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Pairer;
import ar.edu.itba.sia.group3.umbrellaCorporation.VictimPairs;

import java.util.LinkedList;
import java.util.List;

public class DefaultPairer implements Pairer<Character> {
    @Override
    public List<VictimPairs<Character>> getPairs(List<Character> zombies) {
        // Remove one element if not even list
        Character extra = null;
        if(zombies.size()%2==1) {
            extra = zombies.get(0);
            zombies.remove(0);
        }

        //Make pairs
        List<VictimPairs<Character>> pairs = new LinkedList<>();
        for (int i = 0; i<zombies.size()/2;i++)
            pairs.add(
                    new VictimPairs<Character>(zombies.get(i),zombies.get((zombies.size()-i-1)))
            );

        if(extra!=null) pairs.add(new VictimPairs<>(extra,null));

        return pairs;
    }
}
