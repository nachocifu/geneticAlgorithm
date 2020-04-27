package ar.edu.itba.sia.group3.StopConditions;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.StopCondition;

import java.util.List;

public class TimeStopCondition implements StopCondition<Character> {

    private long timeLimit;
    @Override
    public Boolean shouldContinue(List<Character> currentGeneration) {
        System.out.println(timeLimit-(System.currentTimeMillis()-Engine.getRunTime()));
        return this.timeLimit > (System.currentTimeMillis()-Engine.getRunTime());
    }

    public TimeStopCondition(long timeLimit) {
        this.timeLimit = timeLimit;
    }
}
