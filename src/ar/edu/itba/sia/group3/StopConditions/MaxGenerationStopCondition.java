package ar.edu.itba.sia.group3.StopConditions;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.StopCondition;

import java.util.List;

public class MaxGenerationStopCondition implements StopCondition<Character> {

    private final long iterations;

    public MaxGenerationStopCondition(long iterations) {
        this.iterations = iterations;
    }

    @Override
    public Boolean shouldContinue(List<Character> currentGeneration) {
        return this.iterations > Engine.getGenerationNumber();
    }
}
