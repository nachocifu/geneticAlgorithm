package ar.edu.itba.sia.group3.StopConditions;

import ar.edu.itba.sia.group3.umbrellaCorporation.StopCondition;
import ar.edu.itba.sia.group3.Characters.Character;

import java.util.List;

public class ContentStopCondition implements StopCondition<Character> {

    private int currCounter;
    private int contentLimit;
    private double lastMaximumFitness;
    private double maximumFitness;
    public ContentStopCondition(int generations){
        this.contentLimit = generations;
        this.lastMaximumFitness = 0;
        this.currCounter = 0;
        this.maximumFitness = 0;
    }

    @Override
    public Boolean shouldContinue(List<Character> currentGeneration) {

        maximumFitness = getMaximumFitness(currentGeneration);
        System.out.println("CurrentMax:"+maximumFitness+", lastMax:"+lastMaximumFitness+" Counter:"+currCounter);

        if(lastMaximumFitness == 0){ //first iteration
            lastMaximumFitness = maximumFitness;
            return true;
        }

        if(lastMaximumFitness == maximumFitness){
            currCounter++;
        }
        else {
            lastMaximumFitness = maximumFitness;
            currCounter = 0;
        }

        return currCounter < contentLimit;
    }

    private static double getMaximumFitness(List<Character> currentGeneration){
        return currentGeneration.parallelStream().mapToDouble(Character::getFitness).max().orElse(0);
    }
}
