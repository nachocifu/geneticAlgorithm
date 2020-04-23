package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class TorneoProbabilistico implements Selector<Character> {

    private final int k;
    private final double thresHold;

    public TorneoProbabilistico(int k, double th) {
        this.k = k;
        this.thresHold = th;
    }


    @Override
    public List<Character> select(List<Character> currentGeneration) {

        List<Character> selectedCharacters = new LinkedList<Character>();
        Random random = new Random();
        int first,second;

        for (int index =0; index<k;index++){

            // Get the two random characters
            first = random.nextInt(currentGeneration.size());
            do{
                second = random.nextInt(currentGeneration.size());
            } while (second == first);

            // Decide winner
            if(random.nextDouble()<thresHold){
                //keep the best
                if(currentGeneration.get(first).getFitness()>=currentGeneration.get(second).getFitness())
                    selectedCharacters.add(currentGeneration.get(first));
                else
                    selectedCharacters.add(currentGeneration.get(second));

            } else {
                // keep the worst
                if(currentGeneration.get(first).getFitness()>=currentGeneration.get(second).getFitness())
                    selectedCharacters.add(currentGeneration.get(second));
                else
                    selectedCharacters.add(currentGeneration.get(first));
            }
        }

        return selectedCharacters.parallelStream().map(
                character -> {
                    try {
                        return character.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
    }

}
