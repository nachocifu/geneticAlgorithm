package ar.edu.itba.sia.group3;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.Combiners.FillAll;
import ar.edu.itba.sia.group3.Combiners.FillParent;
import ar.edu.itba.sia.group3.Mutators.CompleteMutator;
import ar.edu.itba.sia.group3.Mutators.MultiGenMutator;
import ar.edu.itba.sia.group3.Mutators.SingleGenMutator;
import ar.edu.itba.sia.group3.Mutators.UniformMutator;
import ar.edu.itba.sia.group3.Selectors.*;
import ar.edu.itba.sia.group3.StopConditions.*;
import ar.edu.itba.sia.group3.umbrellaCorporation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Configuration {

    private static Map<String,String> parameters = new HashMap<>();

    public Configuration() {
        throw new UnsupportedOperationException();
    }

    public static void readConfigurationFile() throws IOException {

        String fileName = "ConfigurationFile.txt";
        String currentLine;
        String[] params;

        BufferedReader objReader = new BufferedReader(new FileReader(fileName));

        while ((currentLine = objReader.readLine()) != null) {
            if(!currentLine.startsWith("#") && !currentLine.trim().equals("")){
               params = currentLine.split(" ");
               parameters.put(params[0],params[2]);
            }
        }
        validateParameters();

    }

    public static Map<CharacteristicType,Map<Integer,Characteristic>> getCharacteristics() throws IOException {
        Map<CharacteristicType,Map<Integer,Characteristic>> characteristics = new HashMap<>();
        characteristics.put(CharacteristicType.CHESTPLATE,getCharacteristic("testdata\\pecheras.tsv"));
        characteristics.put(CharacteristicType.WEAPON,getCharacteristic("testdata\\armas.tsv"));
        characteristics.put(CharacteristicType.BOOTS,getCharacteristic("testdata\\botas.tsv")); //resolve?
        characteristics.put(CharacteristicType.GAUNTLETS,getCharacteristic("testdata\\guantes.tsv"));
        characteristics.put(CharacteristicType.HELMET,getCharacteristic("testdata\\cascos.tsv"));
        return characteristics;
    }

    private static Map<Integer,Characteristic> getCharacteristic(String path) throws IOException {
        Map<Integer,Characteristic> characteristicElements = new HashMap<>();
        String currentLine;
        String[] params;

        BufferedReader objReader = new BufferedReader(new FileReader(path));
        currentLine = objReader.readLine(); //ignore first line
        while ((currentLine = objReader.readLine()) != null) {
            params = currentLine.split(" ");
            characteristicElements.put(Integer.parseInt(params[0]),new Characteristic(Double.parseDouble(params[1]),Double.parseDouble(params[2]),Double.parseDouble(params[3]),Double.parseDouble(params[4]),Double.parseDouble(params[5]),Long.parseLong(params[0])));
        }
        return characteristicElements;
    }

    public static String getParameterValue(String parameter){
        if(parameters.containsKey(parameter)){
            return parameters.get(parameter);
        }
        return "";
    }

    public static Selector<Character> getSelector(){
        String selector = parameters.get("selector_1");
        String selector2 = parameters.get("selector_2");
        if(selector2.equals("none")){
            return createSelector(selector);
        }
        return new Hybrid(createSelector(selector),createSelector(selector2),Double.parseDouble(parameters.get("A")));
    }

    private static Selector<Character> createSelector(String selector){ //lo mismo con el parametro de character
        switch (selector){
            case "Boltzmann":
                return new RuletaBoltzmannSelector(Integer.parseInt(parameters.get("K")));
            case "elite":
                return new EliteSelector(Integer.parseInt(parameters.get("K")));
            case "ranking":
                return new Ranking(Integer.parseInt(parameters.get("K")));
            case "roulette":
                return new Ruleta(Integer.parseInt(parameters.get("K")));
            case "deterministic_tournament":
                return new TorneoDeterministico(Integer.parseInt(parameters.get("K")),Integer.parseInt(parameters.get("M")));
            case "stochastic_tournament":
                return new TorneoProbabilistico(Integer.parseInt(parameters.get("K")),Double.parseDouble(parameters.get("threshold")));
            case "universal":
                return new Universal(Integer.parseInt(parameters.get("K")));
            default:
                return null;
        }
    }

    public static Mutator getMutator(){
        String mutator = parameters.get("mutator");
        double mutationProbability = Double.parseDouble("mutation_probability");

        switch (mutator){
            case "uniform":
                return new UniformMutator(mutationProbability);
            case "single_gen":
                return new SingleGenMutator(mutationProbability);
            case "multi_gen":
                return new MultiGenMutator(mutationProbability);
            case "complete":
                return new CompleteMutator(mutationProbability);
            default:
                return null;
        }
    }

    public static Combiner<Character> getCombiner(){
        String selector = parameters.get("replacer_1");
        String selector2 = parameters.get("replacer_2");
        Selector s;

        if(selector2.equals("none")){
             s = createSelector(selector);
        }
        else{
            s = Hybrid(createSelector(selector),createSelector(selector2),Double.parseDouble(parameters.get("B")));
        }
        return createCombiner(s);
    }

    private static Combiner<Character> createCombiner(Selector<Character> selector){ //es de character o hay que poner un generico extends?
        String combiner = parameters.get("implementation");
        switch (combiner){
            case "fill_all":
                return new FillAll(Integer.parseInt(parameters.get("N")),selector);
            case "fill_parent":
                return new FillParent(Integer.parseInt(parameters.get("N")),selector);
            default:
                return null;
        }
    }

    public static Breeder getBreeder(){
        String breeder = parameters.get("crosser");
        double recombinationProbability = Double.parseDouble(parameters.get("recombination_probability"));
        switch (breeder){
            case "annular":
//                return new AnnularBreeder(recombinationProbability);
            case "single_point":
//                return new SinglePointBreeder(recombinationProbability);
            case "double_point":
//                return new DoublePointBreeder(recombinationProbability);
            case "uniform":
//                return new UniformBreeder(recombinationProbability);
            default:
                return null;
        }
    }

    public static StopCondition getStopCondition(){
        String stopCondition = parameters.get("stop_condition");

        switch (stopCondition){
            case "content":
                return new ContentStopCondition(Integer.parseInt(parameters.get("content_count")));
            case "max_generation":
                return new MaxGenerationStopCondition(Integer.valueOf(parameters.get("max_generation")));
            case "structure":
                return new StructureStopCondition(Double.parseDouble(parameters.get("structure_size_percent")),Integer.valueOf(parameters.get("structure_count")));
            case "time":
                return new TimeStopCondition(Long.parseLong(parameters.get("time")));
            case "acceptable_solution":
                return new AcceptableSolutionStopCondition(Double.parseDouble(parameters.get("fitness")));
            default:
                return null;
        }
    }

    //TODO agregar validaciones faltantes: M,N,fitness y seguro hay alguna mas
    private static void validateParameters(){
        //selectors
        if(!parameters.get("selector_1").equals("elite") && !parameters.get("selector_1").equals("ranking") && !parameters.get("selector_1").equals("deterministic_tournament") && !parameters.get("selector_1").equals("stochastic_tournament")
                && !parameters.get("selector_1").equals("roulette") && !parameters.get("selector_1").equals("universal") && !parameters.get("selector_1").equals("Boltzmann")){
            throw new IllegalArgumentException("invalid selector_1");
        }

        if(!parameters.get("selector_2").equals("elite") && !parameters.get("selector_2").equals("ranking") && !parameters.get("selector_2").equals("deterministic_tournament") && !parameters.get("selector_2").equals("stochastic_tournament")
                && !parameters.get("selector_2").equals("roulette") && !parameters.get("selector_2").equals("universal") && !parameters.get("selector_2").equals("Boltzmann") && !parameters.get("selector_2").equals("none")){
            throw new IllegalArgumentException("invalid selector_2");
        }
        if(parameters.get("selector_2").equals("none") && !parameters.get("A").equals("1")){
            throw new IllegalArgumentException("invalid A ratio");
        }
        if(Double.parseDouble(parameters.get("A")) > 1 || Double.parseDouble(parameters.get("A")) < 0){
            throw new IllegalArgumentException("invalid A ratio");
        }

        //replacers
        if(!parameters.get("replacer_1").equals("elite") && !parameters.get("replacer_1").equals("ranking") && !parameters.get("replacer_1").equals("deterministic_tournament") && !parameters.get("replacer_1").equals("stochastic_tournament")
                && !parameters.get("replacer_1").equals("roulette") && !parameters.get("replacer_1").equals("universal") && !parameters.get("replacer_1").equals("Boltzmann")){
            throw new IllegalArgumentException("invalid replacer_1");
        }

        if(!parameters.get("replacer_2").equals("elite") && !parameters.get("replacer_2").equals("ranking") && !parameters.get("replacer_2").equals("deterministic_tournament") && !parameters.get("replacer_2").equals("stochastic_tournament")
                && !parameters.get("replacer_2").equals("roulette") && !parameters.get("replacer_2").equals("universal") && !parameters.get("replacer_2").equals("Boltzmann") && !parameters.get("replacer_2").equals("none")){
            throw new IllegalArgumentException("invalid replacer_2");
        }
        if(parameters.get("replacer_2").equals("none") && !parameters.get("B").equals("1")){
            throw new IllegalArgumentException("invalid B ratio");
        }
        if(Double.parseDouble(parameters.get("B")) < 0 || Double.parseDouble(parameters.get("B")) > 1){
            throw new IllegalArgumentException(("invalid B ratio"));
        }

        //implementations
        if(!parameters.get("implementation").equals("fill_all") && !parameters.get("implementation").equals("fill_parent")){
            throw new IllegalArgumentException("invalid implementation");
        }
        if(Integer.parseInt(parameters.get("K")) <= 0){
            throw new IllegalArgumentException("invalid K");
        }

        //crosser
        if(!parameters.get("crosser").equals("single_point") && !parameters.get("crosser").equals("double_point") && !parameters.get("crosser").equals("uniform") && !parameters.get("crosser").equals("annular")){
            throw new IllegalArgumentException("invalid crosser");
        }
        if(Double.parseDouble(parameters.get("recombination_probability")) > 1 && Double.parseDouble(parameters.get("recombination_probability")) < 0){
            throw new IllegalArgumentException("invalid recombination_probability");
        }

        //mutator
        if(!parameters.get("mutator").equals("single_gen") && !parameters.get("mutator").equals("multi_gen") && !parameters.get("mutator").equals("uniform")
                && !parameters.get("mutator").equals("complete")){
            throw new IllegalArgumentException("invalid mutator");
        }
        if(Double.parseDouble(parameters.get("mutation_probability")) > 1 || Double.parseDouble(parameters.get("mutation_probability")) < 0){
            throw new IllegalArgumentException("invalid mutation_probability");
        }

        //stop condition
        if(!parameters.get("stop_condition").equals("time") && !parameters.get("stop_condition").equals("max_generation") && !parameters.get("stop_condition").equals("structure") && !parameters.get("stop_condition").equals("content") &&
        !parameters.get("stop_condition").equals("optimal") && !parameters.get("stop_condition").equals("acceptable")){
            throw new IllegalArgumentException("invalid stop_condition");
        }
        if(parameters.get("stop_condition").equals("time") && Integer.parseInt(parameters.get("time")) < 0){
            throw new IllegalArgumentException("invalid time value");
        }
        if(parameters.get("stop_condition").equals("max_generation") && Integer.parseInt(parameters.get("max_generation")) < 0){
            throw new IllegalArgumentException("invalid max_generation value");
        }
        if(parameters.get("stop_condition").equals("content") && Integer.parseInt(parameters.get("content_count")) < 0){
            throw new IllegalArgumentException("invalid content value");
        }
        if(parameters.get("stop_condition").equals("structure") && (Double.parseDouble(parameters.get("structure_size_percent")) < 0 || Double.parseDouble(parameters.get("structure_size_percent")) > 1)){
            throw new IllegalArgumentException("structure_size_percent value");
        }
    }
}
