package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class Ranking implements Selector<Character> {

    private final int k;

    public Ranking(int k) {
        this.k = k;
    }


    /**
     * Hacemos lo mismo que Ruleta normal pero usamos posision y cantidad en vez de fitness
     * @param currentGeneration
     * @return
     * @throws Exception
     */
    public List<Character> select(List<Character> currentGeneration) throws Exception {

        // Primero ordenamos segun el fitness
        Collections.sort(currentGeneration, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return (int) (o2.getFitness()-o1.getFitness());
            }
        });

        // Aca tenemos la suma total para sacar la proporcion
        double totalSum = 0.0;
        // Aca guardamos los acumulados como en Proba
        double[] acumulatedSum = new double[currentGeneration.size()];

        for (int index = 0; index < currentGeneration.size(); index++) {
            totalSum += index+1;
            acumulatedSum[index] = totalSum;
        }

        List<Character> selectedCharacters = new LinkedList<Character>();
        for (int x = 0; x < k; x++) {
            int findIndex = Arrays.binarySearch(acumulatedSum, (totalSum) * (new Random()).nextDouble());
            selectedCharacters.add(
                    currentGeneration.get(findIndex < 0 ? -(findIndex + 1) : findIndex)
            );
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
