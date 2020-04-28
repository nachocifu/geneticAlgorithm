package ar.edu.itba.sia.group3;

import ar.edu.itba.sia.group3.Characters.Character;
import ar.edu.itba.sia.group3.Characters.Characteristic;
import ar.edu.itba.sia.group3.Characters.CharacteristicType;
import ar.edu.itba.sia.group3.Characters.Warrior;
import ar.edu.itba.sia.group3.Combiners.FillAll;
import ar.edu.itba.sia.group3.Combiners.FillParent;
import ar.edu.itba.sia.group3.Crossers.Annular;
import ar.edu.itba.sia.group3.Crossers.DoublePoint;
import ar.edu.itba.sia.group3.Crossers.SinglePoint;
import ar.edu.itba.sia.group3.Crossers.Uniform;
import ar.edu.itba.sia.group3.Mutators.CompleteMutator;
import ar.edu.itba.sia.group3.Mutators.MultiGenMutator;
import ar.edu.itba.sia.group3.Mutators.SingleGenMutator;
import ar.edu.itba.sia.group3.Mutators.UniformMutator;
import ar.edu.itba.sia.group3.Pairers.DefaultPairer;
import ar.edu.itba.sia.group3.Selectors.*;
import ar.edu.itba.sia.group3.StopConditions.*;
import ar.edu.itba.sia.group3.umbrellaCorporation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static Map<CharacteristicType,Map<Integer,Characteristic>> characteristicTypeMapMap;
    public static Map<CharacteristicType,Map<Integer,Characteristic>> getCharacteristics() throws IOException {
        if(characteristicTypeMapMap == null)
            characteristicTypeMapMap = handleCharacteristics();
        return characteristicTypeMapMap;
    }

    private static Map<CharacteristicType,Map<Integer,Characteristic>> handleCharacteristics() throws IOException {
        String dataDir = "realdata";
        Map<CharacteristicType,Map<Integer,Characteristic>> characteristics = new HashMap<>();
        Path root = Paths.get(".").normalize().toAbsolutePath();
        characteristics.put(CharacteristicType.CHESTPLATE,getCharacteristic(root+File.separator+dataDir+File.separator+"pecheras.tsv"));
        characteristics.put(CharacteristicType.WEAPON,getCharacteristic(root+File.separator+dataDir+File.separator+"armas.tsv"));
        characteristics.put(CharacteristicType.BOOTS,getCharacteristic(root+File.separator+dataDir+File.separator+"botas.tsv")); //resolve?
        characteristics.put(CharacteristicType.GAUNTLETS,getCharacteristic(root+ File.separator+dataDir+File.separator+"guantes.tsv"));
        characteristics.put(CharacteristicType.HELMET,getCharacteristic(root+File.separator+dataDir+File.separator+"cascos.tsv"));
        return characteristics;
    }

    private static Map<Integer,Characteristic> getCharacteristic(String path) throws IOException {
        Map<Integer,Characteristic> characteristicElements = new HashMap<>();
        String currentLine;
        String[] params;

        BufferedReader objReader = new BufferedReader(new FileReader(path));
        currentLine = objReader.readLine(); //ignore first line
        while ((currentLine = objReader.readLine()) != null) {
            params = currentLine.split("\t");
            characteristicElements.put(Integer.parseInt(params[0]),new Characteristic(Double.parseDouble(params[1]),Double.parseDouble(params[2]),Double.parseDouble(params[3]),Double.parseDouble(params[4]),Double.parseDouble(params[5]),Long.parseLong(params[0])));
        }
        return characteristicElements;
    }

    public static String getCharacterClass(){
        return parameters.get("character");
    }

    public static Selector<Character> getSelector(){
        String selector = parameters.get("selector_1");
        String selector2 = parameters.get("selector_2");
        Double K = Double.parseDouble(parameters.get("K"));
        Double A = Double.parseDouble(parameters.get("A"));

        if(selector2.equals("none")){
            return createSelector(selector, K);
        }
        return new Hybrid(
                createSelector(
                        selector,
                        Math.ceil(K*A)
                ),
                createSelector(
                        selector2,
                        Math.floor(K*(1-A))
                )
        );
    }

    private static Selector<Character> createSelector(String selector, Double K){ //lo mismo con el parametro de character
        switch (selector){
            case "boltzmann":
                return new Boltzmann(Integer.parseInt(parameters.get("K")));
            case "elite":
                return new EliteSelector(K.intValue());
            case "ranking":
                return new Ranking(K.intValue());
            case "roulette":
                return new Ruleta(K.intValue());
            case "deterministic_tournament":
                return new TorneoDeterministico(K.intValue(),Integer.parseInt(parameters.get("M")));
            case "stochastic_tournament":
                return new TorneoProbabilistico(K.intValue(),Double.parseDouble(parameters.get("threshold")));
            case "universal":
                return new Universal(K.intValue());
            default:
                return null;
        }
    }

    public static Mutator<Character> getMutator(){
        String mutator = parameters.get("mutator");
        double mutationProbability = Double.parseDouble(parameters.get("mutation_probability"));

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
        Selector<Character> s;
        Double N = Double.parseDouble(parameters.get("populationSize"));
        Double K = Double.parseDouble(parameters.get("K"));
        Double B = Double.parseDouble(parameters.get("B"));

        //Figure out K
        //For FillParent The selectors K value should be N if K>N or N-K if N>K
        //For FillAll K=N
        if(parameters.get("implementation").toString().equals("fill_all"))
            K = N;
        else
            if(K>=N)
                K = N;
            else
                K = N-K;


        if(selector2.equals("none")){
             s = createSelector(selector, K);
        }
        else{
            s = new Hybrid(
                    createSelector(
                            selector,
                            Math.ceil(B*K)
                    ),
                    createSelector(
                            selector2,
                            Math.floor((1-B)*K)
                    )
            );
        }
        return createCombiner(s);
    }

    public static Pairer<Character> getPairer(){
        return new DefaultPairer();
    }

    private static Combiner<Character> createCombiner(Selector<Character> selector){ //es de character o hay que poner un generico extends?
        String combiner = parameters.get("implementation");
        switch (combiner){
            case "fill_all":
                return new FillAll(selector);
            case "fill_parent":
                return new FillParent(Integer.parseInt(parameters.get("populationSize")),selector);
            default:
                return null;
        }
    }

    public static Breeder<Character> getBreeder(){
        String breeder = parameters.get("crosser");
        switch (breeder){
            case "annular":
                return new Annular();
            case "single_point":
                return new SinglePoint();
            case "double_point":
                return new DoublePoint();
            case "uniform":
                return new Uniform(Double.parseDouble(parameters.get("crosser_probability")));
            default:
                return null;
        }
    }

    public static StopCondition<Character> getStopCondition(){
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
                && !parameters.get("selector_1").equals("roulette") && !parameters.get("selector_1").equals("universal") && !parameters.get("selector_1").equals("boltzmann")){
            throw new IllegalArgumentException("invalid selector_1");
        }

        if(!parameters.get("selector_2").equals("elite") && !parameters.get("selector_2").equals("ranking") && !parameters.get("selector_2").equals("deterministic_tournament") && !parameters.get("selector_2").equals("stochastic_tournament")
                && !parameters.get("selector_2").equals("roulette") && !parameters.get("selector_2").equals("universal") && !parameters.get("selector_2").equals("boltzmann") && !parameters.get("selector_2").equals("none")){
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

    public static int initialPopulationSize() {
        return Integer.parseInt(parameters.get("populationSize"));
    }
}
