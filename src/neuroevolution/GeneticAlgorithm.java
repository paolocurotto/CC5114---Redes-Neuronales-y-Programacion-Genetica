package neuroevolution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class GeneticAlgorithm {

    public int pop_size = 20; // Population size
    public int k; // For tournament selection
    public List<Individual> population = new ArrayList<>();
    public List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation

    int generationCounter = 0;
    int fitnessTarget = 10;

    public GeneticAlgorithm() {
        IntStream.range(0, pop_size).forEach(i -> population.add(new Individual()));

    }

    /** Genetic algorithm **/
    public void select() {

        // Tournament selection
        for (int n = 0; n < pop_size; n++) {
            // Find dad
            int father_index = -1;
            for (int i = 0; i < k; i++) {
                int best_index = ThreadLocalRandom.current().nextInt(0, pop_size);
                if (father_index == -1 || population.get(best_index).fitness > population.get(father_index).fitness) {
                    father_index = best_index;
                }
            }
            // Find mom
            int mother_index = -1;
            for (int i = 0; i < k; i++) {
                int best_index = (father_index + ThreadLocalRandom.current().nextInt(1, pop_size)) % pop_size;
                if (i == 0 || population.get(best_index).fitness > population.get(mother_index).fitness) {
                    mother_index = best_index;
                }
            }
            // Reproduction
            Individual baby = population.get(father_index).breed(population.get(mother_index));
            new_generation.add(baby);
        }
        // Set offspring as the current generation and kill old generation
        population.clear();
        population.addAll(new_generation);
        new_generation.clear();

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(fitnessTarget));
        population.sort((a, b) ->  b.fitness - a.fitness);

        // Print info
        System.out.println("Generation: " + ++generationCounter +
                ", best/target: (" + population.get(0).fitness + "/" + fitnessTarget + ")");

    }
}
