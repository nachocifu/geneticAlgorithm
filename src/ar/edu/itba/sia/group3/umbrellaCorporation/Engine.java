package ar.edu.itba.sia.group3.umbrellaCorporation;

import java.util.List;

/**
 * This class will handle the actual steps of the algorithm using the interfaces provided by umbrella.
 */
public class Engine<E extends Victim> {

    Selector<E> selector;
    Pairer<E> pairer;
    Breeder<E> breeder;
    Mutator<E> mutator;
    Combiner<E> combiner;
    StopCondition<E> stopCondition;


    //constructor de setup que recibira toda la info para correr con interfaces copadas de la empresa
    public Engine(){
        printLogo();
    }

    //metoddo run que correra el algoritmo
    public List<E> run(List<E> currentGeneration){

        List<E> zombies; // ----> the ones being changed by Umbrella
        List<VictimPairs<E>> pairedZombies;

        do{

            // Select the zombies umbrella will experiment on
            zombies = selector.select(currentGeneration);
            // Group zombies for metamorphosis
            pairedZombies = pairer.getPairs(zombies);
            // Morph them zombies
            zombies = breeder.breed(pairedZombies);
            // Mutate any defectives?
            zombies = mutator.mutate(zombies);
            // Generate the new flock of invading zombies
            currentGeneration = combiner.combine(currentGeneration, zombies);

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
