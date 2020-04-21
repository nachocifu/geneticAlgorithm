package ar.edu.itba.sia.group3.StopConditions;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.umbrellaCorporation.Engine;
import ar.edu.itba.sia.group3.umbrellaCorporation.StopCondition;
import java.util.LinkedList;
import java.util.List;

public class StructureStopCondition implements StopCondition<Character> {

    private int generations;
    private int currCount;
    private double percent;
    private int equalsCondition;
    private List<Character> lastGeneration;

    public StructureStopCondition(double percent,int generations){
        this.generations = generations;
        this.percent = percent;
        this.currCount = 0;
        this.equalsCondition = 0;
        this.lastGeneration = new LinkedList<>();
    }

    @Override
    public Boolean shouldContinue(List<Character> currentGeneration) {

        if(Engine.getGenerationNumber() == 1) //first iteration completed
        {
            lastGeneration.addAll(currentGeneration);
            return true;
        }

        equalsCondition = (int) (percent * currentGeneration.size());
        for(Character c : currentGeneration)
        {
            if(lastGeneration.contains(c)){
                if(equalsCondition > 0){
                    equalsCondition--;
                }
                else {
                    currCount++;
                    break;
                }
            }
        }

        if(equalsCondition > 0){
            currCount = 0;
            lastGeneration.addAll(currentGeneration);
        }


        return currCount < generations;
    }
}
