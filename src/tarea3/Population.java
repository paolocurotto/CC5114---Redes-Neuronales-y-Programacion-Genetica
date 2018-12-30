package tarea3;

import neural_network.SigmoidNeuron;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static tarea3.Globals.*;
import static tarea3.Globals.Mutation.*;

public class Population {

    List<Individual> individuals = new ArrayList<>();
    List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
    int generationCounter = 0;

    Population() {
        IntStream.range(0, POPULATION_SIZE).forEach(i -> individuals.add(new Individual()));
    }

    private void calculateFitness(Individual individual) {
        individual.fitness = individual.hits + 0.0001 * individual.time_idle;
    }

    /** Genetic algorithm **/
    void startNewGeneration() {
        // Calculate fitness
        individuals.forEach(this::calculateFitness);
        individuals.sort((a, b) -> (b.fitness - a.fitness > 0) ? 1 : (b.fitness < a.fitness) ? -1 : 0);
        System.out.println("Generation: " + ++generationCounter + ", best fit: " + individuals.get(0).fitness +
                " | mov used: " + individuals.get(0).mov_used + ", idle: " + individuals.get(0).time_idle
                + "| idle worst: " + individuals.get(individuals.size() - 1).time_idle);

        // Tournament selection
        for (int n = 0; n < POPULATION_SIZE; n++) {
            // Find dad
            int father_index = -1;
            for (int i = 0; i < K_FACTOR; i++) {
                int best_index = ThreadLocalRandom.current().nextInt(0, POPULATION_SIZE);
                if (father_index == -1 || individuals.get(best_index).fitness > individuals.get(father_index).fitness) {
                    father_index = best_index;
                }
            }
            // Find mom
            int mother_index = -1;
            for (int i = 0; i < K_FACTOR; i++) {
                int best_index = (father_index + ThreadLocalRandom.current().nextInt(1, POPULATION_SIZE)) % POPULATION_SIZE;
                if (i == 0 || individuals.get(best_index).fitness > individuals.get(mother_index).fitness) {
                    mother_index = best_index;
                }
            }
            // Reproduction
            Individual baby = breed(individuals.get(father_index), (individuals.get(mother_index)));
            new_generation.add(baby);
        }
        // Set offspring as the current generation and kill old generation
        individuals.clear();
        individuals.addAll(new_generation);
        new_generation.clear();

        // Evaluate fitness
        //individuals.forEach(individual -> individual.calculateFitness(fitnessTarget));
        //individuals.sort((a, b) ->  b.fitness - a.fitness);

        // Print info
        //System.out.println("Generation: " + ++generationCounter + ", best: (" + individuals.get(0).fitness + "/" )");

    }

    Individual breed (Individual father, Individual mother) {
        Individual child = new Individual();
        int n_neurons = father.neuralNetwork.n_neurons;

        int index_a = ThreadLocalRandom.current().nextInt(0, n_neurons);
        int index_b = ThreadLocalRandom.current().nextInt(index_a,  n_neurons);
        int current = 0;
        int n_sign_mutations = 0;
        int n_amp_mutations = 0;
        for (int x = 0; x < child.neuralNetwork.neuralLayers.size(); x++) {

            for (int y = 0; y < child.neuralNetwork.neuralLayers.get(x).neurons.size(); y++) {

                // Pick from mother
                if (current <= index_b && index_a <= current) {
                    SigmoidNeuron hNeuron = mother.neuralNetwork.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.neuralNetwork.neuralLayers.get(x).neurons.set(y, hNeuron);
                    if (hNeuron.mutation == SIGN_INVERT_MUTATION)
                        n_sign_mutations++;
                    if (hNeuron.mutation == AMP_MUTATION)
                        n_amp_mutations++;
                }

                // Pick from father
                else {
                    SigmoidNeuron hNeuron = father.neuralNetwork.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.neuralNetwork.neuralLayers.get(x).neurons.set(y, hNeuron);
                    if (hNeuron.mutation == SIGN_INVERT_MUTATION)
                        n_sign_mutations++;
                    if (hNeuron.mutation == AMP_MUTATION)
                        n_amp_mutations++;
                }
                current++;
            }
        }
        // Set color
        //int r = (Math.random() < 0.5) ? father.color.getRed() : mother.color.getRed();
        //int b = (Math.random() < 0.5) ? father.color.getBlue() : mother.color.getBlue();
        //child.color = new Color(r, 0 , b);
        child.n_sign_mutations = Math.max(father.n_sign_mutations, mother.n_sign_mutations) + n_sign_mutations;
        child.n_amp_mutations = Math.max(father.n_amp_mutations, mother.n_amp_mutations) + n_amp_mutations;
        //child.color = new Color(255 - child.n_sign_mutations, 0, 255 - child.n_amp_mutations);
        return child;
    }

}
