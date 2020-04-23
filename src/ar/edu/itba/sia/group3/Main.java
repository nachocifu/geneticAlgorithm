package ar.edu.itba.sia.group3;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        //Generate initial generation
//        TODO parse a configuration file that has path to dataset and parameters for algorithm.
        try {
            Configuration.readConfigurationFile();
            Configuration.getCharacteristics();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Character> initialGeneration = new ArrayList<Character>();


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
}
