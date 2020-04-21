package ar.edu.itba.sia.group3.StopConditions;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.StopCondition;

import java.util.List;

public class AcceptableSolutionStopCondition implements StopCondition<Character> {

    private double aceptableFitness;

    public AcceptableSolutionStopCondition(double aceptableFitness){
        this.aceptableFitness = aceptableFitness;
    }

    @Override
    public Boolean shouldContinue(List<Character> currentGeneration) {
        for(Character c : currentGeneration){
            if(c.getFitness() >= aceptableFitness){
                return false;
            }
        }
        return true;
    }
}
