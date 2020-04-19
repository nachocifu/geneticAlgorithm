package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Universal implements Selector<Character> {

    private final int k;

    public Universal(int k) {
        this.k = k;
    }


    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {

        // Aca tenemos la suma total para sacar la proporcion
        double totalSum = 0.0;
        // Aca guardamos los acumulados como en Proba
        double[] acumulatedSum = new double[currentGeneration.size()];

        // Aca armamos un pie table. Donde irrelevantemente del orden en que vengan,
        // cada seccion del pie representa a cada character segun la magnitud de su fitness respecto a los demas
        for (int index = 0; index < currentGeneration.size(); index++) {
            Character character = currentGeneration.get(index);
            totalSum += character.getFitness();
            acumulatedSum[index] = totalSum;
        }


        double r = (new Random()).nextDouble();

        List<Character> selectedCharacters = new LinkedList<Character>();
        double rj;
        for (int x = 0; x < k; x++) {
            rj = (r + x - 1)/k;

            int findIndex = Arrays.binarySearch(acumulatedSum, rj);
            selectedCharacters.add(
                    currentGeneration.get(findIndex < 0 ? -(findIndex + 1) : findIndex)
            );
        }

        return selectedCharacters;
    }

}
