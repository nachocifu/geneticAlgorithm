package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class Ruleta implements Selector<Character> {

    private final int k;

    public Ruleta(int k) {
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



        List<Character> selectedCharacters = new LinkedList<Character>();
        for (int x = 0; x < k; x++) {
            // seleccionamos un numero al azar y buscamos a que seccion de la torta pertenece haciendo busqueda binaria
            int findIndex = Arrays.binarySearch(acumulatedSum, (totalSum) * (new Random()).nextDouble());
            selectedCharacters.add(
                    currentGeneration.get(findIndex < 0 ? -(findIndex + 1) : findIndex)
            );
        }

        return selectedCharacters;
    }

}
