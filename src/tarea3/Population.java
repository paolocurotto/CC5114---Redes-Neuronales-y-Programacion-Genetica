package tarea3;

import neural_network.SigmoidNeuron;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static tarea3.Globals.K_FACTOR;
import static tarea3.Globals.POPULATION_SIZE;

public class Population {

    int size = POPULATION_SIZE;
    int k = K_FACTOR;
    List<Individual> individuals = new ArrayList<>();
    List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
    int generationCounter = 0;

    public Population() {
        IntStream.range(0, POPULATION_SIZE).forEach(i -> individuals.add(new Individual()));
    }

    /** Genetic algorithm **/
    public void startNewGeneration() {

        individuals.sort((a, b) ->  b.fitness - a.fitness);
        System.out.println("Generation: " + ++generationCounter + ", best/worst: " + individuals.get(0).fitness + " / "+
                individuals.get(individuals.size() - 1).fitness + " | ");

        // Tournament selection
        for (int n = 0; n < POPULATION_SIZE; n++) {
            // Find dad
            int father_index = -1;
            for (int i = 0; i < k; i++) {
                int best_index = ThreadLocalRandom.current().nextInt(0, POPULATION_SIZE);
                if (father_index == -1 || individuals.get(best_index).fitness > individuals.get(father_index).fitness) {
                    father_index = best_index;
                }
            }
            // Find mom
            int mother_index = -1;
            for (int i = 0; i < k; i++) {
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
        for (int x = 0; x < child.neuralNetwork.neuralLayers.size(); x++) {

            for (int y = 0; y < child.neuralNetwork.neuralLayers.get(x).neurons.size(); y++) {

                // Pick from mother
                if (current <= index_b && index_a <= current) {
                    SigmoidNeuron hNeuron = mother.neuralNetwork.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.neuralNetwork.neuralLayers.get(x).neurons.set(y, hNeuron);
                }

                // Pick from father
                else {
                    SigmoidNeuron hNeuron = father.neuralNetwork.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.neuralNetwork.neuralLayers.get(x).neurons.set(y, hNeuron);
                }
                current++;
            }
        }
        return child;    }

}
