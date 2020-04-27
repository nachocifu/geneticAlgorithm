package ar.edu.itba.sia.group3.Selectors;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.Selector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Boltzmann implements Selector<Character> {

    private final int k;

    public Boltzmann(int k) {
        this.k = k;
    }

    private double T() {
//        return (10-Math.log10(Engine.getGenerationNumber()));//must be a function that decreases as generations pass
        return ((1.0 / 0.001*(Engine.getGenerationNumber())) + 1);
    }

    @Override
    public List<Character> select(List<Character> currentGeneration) throws Exception {
        // Aca tenemos la suma total para sacar la proporcion
        double totalSum = 0.0;
        // Aca guardamos los acumulados como en Proba
        double[] acumulatedSum = new double[currentGeneration.size()];
        // Aca dejo el sigma de Alan, es el promedio de esta generacion
        double avgBoltzmanFitness = currentGeneration.stream().parallel()
                .mapToDouble(Character::getFitness)
                .map(f -> Math.exp(f / T()))
                .average()
                .getAsDouble();

        // Aca armamos un pie table. Donde irrelevantemente del orden en que vengan,
        // cada seccion del pie representa a cada character segun la magnitud de su fitness respecto a los demas
        for (int index = 0; index < currentGeneration.size(); index++) {
            Character character = currentGeneration.get(index);
            totalSum += ( Math.exp(character.getFitness()/T())/avgBoltzmanFitness ); //here we use boltzman temp
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
