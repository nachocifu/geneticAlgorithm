package ar.edu.itba.sia.group3;

import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.Victim;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Generate initial generation
//        TODO parse a configuration file that has path to dataset and parameters for algorithm.
        try {
            Configuration.readConfigurationFile();
            Configuration.getCharacteristics();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Victim> initialGeneration = new ArrayList<Victim>();

	    // Create engine with necessary parameters
        //..
        // TODO parameter generation based on conf
        //..
        Engine<Victim> engine = new Engine<Victim>();

        // Run the Engine
        //List<Victim> evolvedGeneration = engine.run(initialGeneration);

        // Handle algorithm results
        // ...
        // Some shit here...
    }
}
