# Genetic Algorithm Engine
Java university project about finding the best item set for a given character class from
a game using genetic algorithms with a variety of selectors, mutators, breeders, combiners and engine stop conditions.
## Implementation remarks
Developed and tested with Java version 8 on Windows 10, Java version 13 on Mac OS Catalina and Java version 8 on Archlinux(4.19.101-1-lts).
### Character classes
- Archer
- Defender
- Spy
- Warrior
### Implementation modeling
- An item has strength, agility, experience, resistance and life attributes
- A gen is an item from the character item set.
- A character has 6 genes: helmet, chestplate, gauntlets, weapon, height and boots
- Genes from a chromosome can be changed for another item from an existing items pool, except for height which is a number with certain restrictions.
- Fitness depends on character class according to a formula which has attack and defense as variables, which depend on items attributes and an attack or defense modifier. which are calculated from height gen.
### Selectors implemented
- Elite selection: selects a given amount of individuals based on fitness.
- Roulette selection: calculates the accumulated fitness function for each individual in the population
and then chooses k individuals generating for each a random value and searching the individual whose accumulated function is equal o closer to the value
- Ranking selection: calculates the accumulated index function and for each individual generating for each a random value and searching the individual whose accumulated function is equal o closer to the value.
- Universal selection: calculates the accumulated fitness function for each individual in the population, then generates one random number
and places it as a parameter in a selection formula that varies an iteration parameter.
- Boltzmann selection: calculates an acumulated function using e^(f/T)/T where f is fitness and T is a decreasing function
- Deterministic tournament selection: selects a certain amount of individuals from the generation and takes the best. Repeats this process until the given amount is reached.
- Stochastic tournament selection: given a threshold value between 0.5 and 1, selects two individuals and generates a random number between 0 and 1.
if the random number is greater equal than threshold, takes the character with less fitness.
- Hybrid selection: a combination of two selectors with an influence parameter that sets how many individuals each selector selects
### Combiners implemented
- Fill all: choose an amount of individuals equal to population_size from previous population plus the new individuals set.
- Fill parent: chooses all new individuals and completes until population_size selecting from previous population.
### Crossers implemented
- Single point crossing: generates a random number and exchanges genes from that random index until the last characteristic with the selected pair.
- Two point crossing: generates two random numbers and exchanges genes between those indexes with selected pair.
- Annular crossing: generates a random number and exchanges half the total amount of genes from that random index. if the amount of genes exceeds max index, it behaves circularly.
- Uniform crossing: generates a random number for each gen. if the number is less than a given parameter, it exchanges the gen.
Individuals are shuffled a taken from a list for pairing, the first is paired with the last, and so on.
if there is an odd number of individuals, one is left alone.
### Mutators implemented
- Single gen mutator: a single gen from a character can change depending on a given probability.
- Multi gen mutator: a random amount of genes from a character can change depending on a given probability.
- Uniform mutator: each character gen can change with a given probability.
- Complete mutator: all character genes change with a given probability.
### Stop conditions implemented
- Acceptable solution stop condition: the engine stops when finds an individual in the generation with a fitness greater equal than the given parameter.
- Content stop condition: the engine stops if after a given number of generations the maximum fitness remains the same.
- Maximum generation stop condition: the engine stops after a given number of generations.
- Structure stop condition: given a number of generations and a percent value, the engine stops if after that amount of generations that given percent of the individuals remain the same.
- Time stop condition: the engine stops after a certain amount of time.
### Compiling
The following applies to unix based systems.
First create on the root of the project a classes directory `mkdir classes`.
Then choose desired settings for the engine in ConfigurationFile.txt.
To compile the project run: `javac src/ar/edu/itba/sia/group3/*.java -d classes`.
Then change directory to the classes directory generated. `cd classes`.
Your ready to run the program with `java ar.edu.itba.sia.group3.Main`.
### Parameters
- data_dir: path where data files are located.
- Population_size.
#### Character Type
- Warrior.
- archer.
- defense.
-  spy
#### Selectors
two selectors and a percent parameter can be chosen.
- A: is what % assigned to selector_1 and 1-A % assigned to selector_2.
- elite.
- ranking.
- deterministic_tournament.
- stochastic_tournament.
- roulette.
- universal.
- boltzmann.
- none: only the second possible selector can have this value.
- K: individuals selected from population by selector.
- M: individuals participating in tournaments tournament, used only if a tournament selector is chosen either as selector_1 or selector_2.
- threshold: value for stochastic tournament selector.
#### Combiners
There are two ways of combining previous population with new individuals. Both can use the same range of selectors in selectors as replacers.
There can be up to two replacers and there is a parameter to control how many are selected with each.
- fill_parent:
- fill_all:
- replacer_1.
- replacer_2.
- B is what percent is assigned to replacer_1 and 1-B is what is assigned to replacer_2.
#### Crosser
- single_point.
- double_point.
- uniform.
- annular.
- crosser_probability: number that determines whether a gen is exchanged or not applies only in uniform.
####Mutator
- uniform.
- single_gen mutation.
- multi_gen.
- complete.
- mutation_probability: probability of mutation.
#####Stop condition
- time.
- max_generation.
- structure.
- content.
- acceptable_solution.
- structure_size_percent: if this percent of the population_size does not change after structure_count generations, the engine stops. Applies only in structure stop condition.
- structure_count: applies only on structure stop condition.
- content_count: number of generations in which, if best fitness has not changed the engine stops. Applies only in content stop condition.
- max_generation: limit of generations. Applies only in max_generation stop condition.
- time: in ms. Applies only in time stop condition.
- fitness: fitness value for acceptable solution. Applies only in acceptable_solution stop condition.
### Example of running
populationSize = 3
character = archer
A = 1
selector_1 = elite
selector_2 = none
K = 2
B = 1
replacer_1 = ranking
replacer_2 = none
implementation = fill_parent
crosser = uniform
crosser_probability = 0.5
mutator = uniform
mutation_probability = 0.5
stop_condition = structure
structure_size_percent = 0.85
structure_count = 10
### Output
Once finalized, output consist of maximum fitness achieved, minimum fitness, fitness sum and average fitness of the final population.

