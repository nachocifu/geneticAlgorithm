package ar.edu.itba.sia.group3;

import ar.edu.itba.sia.group3.Characters.*;
import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {

        //Generate initial generation
//        TODO parse a configuration file that has path to dataset and parameters for algorithm.
        try {
            Configuration.readConfigurationFile();
            Configuration.getCharacteristics();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        List<Character> initialGeneration = getInitialGeneration();


	    // Create engine with necessary parameters
        //..
        // TODO parameter generation based on conf
        //..
        Engine<Character> engine = new Engine<Character>(
                Configuration.getSelector(),
                Configuration.getPairer(),
                Configuration.getBreeder(),
                Configuration.getMutator(),
                Configuration.getCombiner(),
                Configuration.getStopCondition()
        );

        // Run the Engine
        List<Character> evolvedGeneration = engine.run(initialGeneration);

        System.out.println("Initial Size: "+initialGeneration.size());
        System.out.println("Final Size: "+evolvedGeneration.size());
        System.out.println("~~ Done ~~");

        // Handle algorithm results
        // ...
        // Some shit here...
    }

    /**
     * Inspired on old TPE
     * @return
     */
    private static List<Character> getInitialGeneration() throws Exception {
        int N = Configuration.initialPopulationSize();

        return IntStream.range(0,N).parallel()
                .mapToObj(operand -> {//Ignore operand, we only care that this is called N times possibly with multithreading
                            switch (Configuration.getCharacterClass()) {
                                case "warrior": return new Warrior(Characteristic.getRandomSet());
                                case "archer": return new Archer(Characteristic.getRandomSet());
                                case "defender": return new Defender(Characteristic.getRandomSet());
                                case "spy": return new Spy(Characteristic.getRandomSet());
                                default:
                                    return null;
                            }
                        }
                ).collect(Collectors.toList()
                );
    }
}
