package ar.edu.itba.sia.group3.Crossers;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Breeder;
import ar.edu.itba.sia.group3.umbrellaCorporation.VictimPairs;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Uniform implements Breeder<Character> {

    private int threshold;

    /**
     *
     * @param threshold between [0,1]
     */
    public Uniform(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<Character> breed(List<VictimPairs<Character>> victimPairs) {
        List<Character> aux = new LinkedList<>();

        Random random = new Random();

        for ( VictimPairs<Character> pair: victimPairs ) {
            if(pair.b==null) {
                aux.add(pair.a);
                continue;
            }

            for (
                    int i = 0;
                    i < (CharacteristicType.values().length-1);
                    i++
            ) {
                if (random.nextDouble()>threshold) continue;
                CharacteristicType type = CharacteristicType.values()[i];
                Characteristic characteristicA = pair.a.getCharacterAlleles().get(type);
                Characteristic characteristicB = pair.b.getCharacterAlleles().get(type);
                pair.a.getCharacterAlleles().put(type, characteristicB);
                pair.b.getCharacterAlleles().put(type, characteristicA);
            }

        }

        return aux;
    }

}
