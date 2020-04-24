package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

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
    }

    public static int getGenerationNumber(){
        return generationNumber;   //dudas sobre esto
    }


    //metoddo run que correra el algoritmo
    public List<E> run(List<E> currentGeneration) throws Exception {

        List<E> zombies; // ----> the ones being changed by Umbrella
        List<VictimPairs<E>> pairedZombies;
        timeElapsed = System.currentTimeMillis();

        do{

            // Select the zombies umbrella will experiment on
            zombies = selector.select(currentGeneration);
            // Group zombies for metamorphosis
//            pairedZombies = pairer.getPairs(zombies);
//            // Morph them zombies
//            zombies = breeder.breed(pairedZombies);
//            // Mutate any defectives?
//            zombies = mutator.mutate(zombies);
            // Generate the new flock of invading zombies
            currentGeneration = combiner.combine(currentGeneration, zombies);
            generationNumber++;
            // Are they ready for Raccoon City?
        }while (stopCondition.shouldContinue(currentGeneration));

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
}
