package tarea3;

import neural_network.SigmoidNeuron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static tarea3.Globals.*;

public class Population {
    List<Individual> individuals = new ArrayList<>();
    private List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
    private int generationCounter = 0;
    List<Double> data_avg = new ArrayList<>();
    List<Double> data_avg_err = new ArrayList<>();
    List<Double> data_best = new ArrayList<>();

    Population() {
        IntStream.range(0, POPULATION_SIZE).forEach(i -> individuals.add(new Individual()));
        individuals.get(individuals.size() - 1).color =  new Color(90, 255, 90);
    }

    private void calculateFitness(Individual individual) {
        individual.fitness = 2 * individual.hits + individual.acc_distance + 0.001 * individual.time_idle;
    }

    /** Genetic algorithm **/
    void startNewGeneration() {
        // Calculate fitness
        individuals.forEach(this::calculateFitness);
        individuals.sort((a, b) -> (b.fitness - a.fitness > 0) ? 1 : (b.fitness < a.fitness) ? -1 : 0);
        if (generationCounter == 50) {
            showData();
        } else {
            addData();
        }

        System.out.println("Generation: " + ++generationCounter
                + ", best Fit: " + individuals.get(0).fitness
                + ", best hits: " + individuals.get(0).hits
                + ", acc dist: " + individuals.get(0).acc_distance
                + ", idle: " + individuals.get(0).time_idle);

        // Tournament selection
        for (int n = 1; n < POPULATION_SIZE; n++) {
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
        // Keep best from previous generation one
        individuals.get(0).color = new Color(90, 255, 90);
        new_generation.add(clone(individuals.get(0)));

        // Set offspring as the current generation and kill old generation
        individuals.clear();
        individuals.addAll(new_generation);
        new_generation.clear();
    }

    private Individual breed(Individual father, Individual mother) {
        Individual child = new Individual();
        int n_neurons = father.neuralNetwork.n_neurons;
        // Crossover
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
        return child;
    }

    private Individual clone(Individual individual) {
        Individual clone = new Individual();
        for (int x = 0; x < clone.neuralNetwork.neuralLayers.size(); x++) {
            for (int y = 0; y < clone.neuralNetwork.neuralLayers.get(x).neurons.size(); y++) {
                SigmoidNeuron hNeuron = individual.neuralNetwork.neuralLayers.get(x).neurons.get(y).makeClone();
                clone.neuralNetwork.neuralLayers.get(x).neurons.set(y, hNeuron);
            }
        }
        clone.color = individual.color;
        return clone;
    }

    private void addData() {
        List<Double> fits = new ArrayList<>();
        individuals.forEach(individual -> fits.add(individual.fitness));
        double bestFitness = individuals.get(0).fitness;
        double avgFitness = fits.stream().mapToDouble(val -> val).average().orElse(0.0);
        fits.replaceAll(i -> avgFitness - i);
        fits.replaceAll(i -> i*i);
        double sum_err = 0; for (double d : fits) {sum_err+=d;}
        double avrErr =  Math.sqrt(sum_err / (fits.size() - 1));
        data_best.add(bestFitness);
        data_avg.add(avgFitness);
        data_avg_err.add(avrErr);
    }

    private void showData() {
        System.out.println("Data: ");
        System.out.println();
        System.out.print("best_fit = [");
        data_best.forEach(val -> {System.out.print(val + ", ");});
        System.out.println("]");
        System.out.print("avg_fit = [");
        data_avg.forEach(val -> {System.out.print(val + ", ");});
        System.out.println("]");
        System.out.print("avg_err = [");
        data_avg_err.forEach(val -> {System.out.print(val + ", ");});
        System.out.println("]");
        System.out.println();
    }

}
