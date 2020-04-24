package ar.edu.itba.sia.group3.Crossers;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.umbrellaCorporation.Breeder;
import ar.edu.itba.sia.group3.umbrellaCorporation.VictimPairs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SinglePoint implements Breeder<Character> {

    @Override
    public List<Character> breed(List<VictimPairs<Character>> victimPairs) {
        List<Character> aux = new LinkedList<>();

        Random random = new Random();

        for ( VictimPairs<Character> pair: victimPairs ) {

            aux.add(pair.a);
            if(pair.b==null) continue;
            aux.add(pair.b);


            for (
                    int i = random.nextInt((CharacteristicType.values().length-1));
                    i < (CharacteristicType.values().length-1);
                    i++
            ) {

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
