package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * This class will handle the actual steps of the algorithm using the interfaces provided by umbrella.
 */
public class Engine<E extends Victim> {

    private Selector<E> selector;
    private Pairer<E> pairer;
    private Breeder<E> breeder;
    private Mutator<E> mutator;
    private Combiner<E> combiner;
    private StopCondition<E> stopCondition;

    private List<Double> fitnessAvg;
    private List<Long> diversity;
    private List<Double> maxFitness;
    private List<Double> minFitness;

    private static int generationNumber = 0;
    private static long timeElapsed;

    public static long getRunTime(){
        return timeElapsed;
    }

    public Engine(Selector<E> selector, Pairer<E> pairer, Breeder<E> breeder, Mutator<E> mutator, Combiner<E> combiner, StopCondition<E> stopCondition) {
        printLogo();
        this.selector = selector;
        this.pairer = pairer;
        this.breeder = breeder;
        this.mutator = mutator;
        this.combiner = combiner;
        this.stopCondition = stopCondition;

        this.fitnessAvg = new LinkedList();
        this.diversity = new LinkedList<>();
        this.maxFitness = new LinkedList();
        this.minFitness = new LinkedList();
    }

    public static int getGenerationNumber(){
        return generationNumber;   //dudas sobre esto
    }


    //metoddo run que correra el algoritmo
    public List<E> run(List<E> currentGeneration) throws Exception {

        List<E> zombies; // ----> the ones being changed by Umbrella
        List<VictimPairs<E>> pairedZombies;
        timeElapsed = System.currentTimeMillis();
        saveSpecsOfGeneration(currentGeneration);

        do{
            // Select the zombies umbrella will experiment on
            zombies = selector.select(currentGeneration);
            // Group zombies for metamorphosis
//            pairedZombies = pairer.getPairs(zombies);
            // Morph them zombies
//            zombies = breeder.breed(pairedZombies);
            // Mutate any defectives?
//            zombies = mutator.mutate(zombies);
            // Generate the new flock of invading zombies
            currentGeneration = combiner.combine(currentGeneration, zombies);
            generationNumber++;
            saveSpecsOfGeneration(currentGeneration);
            // Are they ready for Raccoon City?
        }while (stopCondition.shouldContinue(currentGeneration));

        saveAllGenerationsData();
        return currentGeneration;
    }

    //getters de las cosas que lleguemos a necesitar



    private void printLogo() {
        System.out.println(
                "        %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@* #%%%%%%%%%%%@  %@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@*    *%%%%%%%%#%     &@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@(        /&%%%%%%&         @@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@(.          /&%%%%&           ,&@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@#%%%%@#      .%%##      ,@&%#%%&@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@%%%%%%%%%#%@# ,&& ,&&%%%%%%%%%%@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@%%%%%%%%%%%%&@,&,%@%%%%%%%%%%%%@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@%%%%##&@/    .&%%/    .&@%%%%%%&@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@%&@*         .@%%%%(          %@%@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@(          &%%%%%%*         .@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@(     ,&%%%%%%%%#      @@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@  .&%%%%%%%%%%#  /@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@@,%%&@@@@@@@&%#(@@@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@(        \n" +
                "        %@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@("
        );
    }

    private void saveSpecsOfGeneration(List<E> list) {
        this.fitnessAvg.add(list.parallelStream().mapToDouble(Victim::getFitness).average().orElse(0));
        this.maxFitness.add(list.parallelStream().mapToDouble(Victim::getFitness).max().orElse(0));
        this.minFitness.add(list.parallelStream().mapToDouble(Victim::getFitness).min().orElse(0));
        this.diversity.add(list.parallelStream().distinct().count());
    }

    private void saveAllGenerationsData() throws IOException {
        Path dir = Paths.get("data");
        if(Files.notExists(dir)) {
            Files.createDirectory(dir);
        }
        Path file = dir.resolve(Files.list(dir).count()+".csv");

        FileWriter fileWriter = new FileWriter(file.toFile());
        fileWriter.write("generation,avg,max,diversity,min\n");
        for (int i = 1; i < fitnessAvg.size(); i++) {
            fileWriter.write(i + "," + this.fitnessAvg.get(i) + "," + maxFitness.get(i) + "," + diversity.get(i) + "," + minFitness.get(i) + "\n");
        }
        fileWriter.close();
    }
}
