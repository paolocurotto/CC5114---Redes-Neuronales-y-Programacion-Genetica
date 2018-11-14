package genetic_algorithm;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BitsSequence {

    public static void main(String[] args) {

        start();
    }

    public static void start() {

        int N = 10; // Size of population
        int[] secretSeq = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Secret bits sequence
        int n_bits = secretSeq.length;
        int generationCounter = 0; // Count generations

        List<Individual> population = new ArrayList<>();
        List<Individual> new_generation = new ArrayList<>();
        List<Individual> temp = new ArrayList<>();

        // Initialize population
        for (int i = 0; i < N; i++) {
            population.add(new Individual(n_bits));
        }

        // Evaluate fitness
        for (Individual individual : population) {
            individual.calculateFitness(secretSeq);
        }
        Collections.sort(population);

        // Solution found?
        boolean solutionFound = false;
        if (population.get(0).fitness == n_bits) {
            solutionFound = true;
        }

        print_info(generationCounter, population);


        /** Genetic algorithm **/
        while (solutionFound == false) {

            // Selection
            int select_from_best_of = N / 5;
            for (int i = 0; i < N; i++) {
                Individual m = population.get(ThreadLocalRandom.current().nextInt(0, select_from_best_of));
                Individual f = population.get(ThreadLocalRandom.current().nextInt(0, select_from_best_of));
                while (m.equals(f) == true) {
                    f = population.get(ThreadLocalRandom.current().nextInt(0, select_from_best_of));
                }

                // Reproduction
                Individual new_individual = m.breed(f);
                new_generation.add(new_individual);
            }

            temp = population;
            population = new_generation;
            new_generation = temp;
            new_generation.clear();

            // Evaluate fitness
            for (Individual individual : population) {
                individual.calculateFitness(secretSeq);
            }
            Collections.sort(population);

            // Solution found?
            if (population.get(0).fitness == n_bits) {
                solutionFound = true;
            }

            print_info(++generationCounter, population);

        }

        System.out.println("Finished.");

    }


    public static void print_info(int c, List<Individual> pop) {
        System.out.print("Generation: " + c + ", best: [");
        for (int i = 0; i < pop.get(0).genes.size() - 1; i++) {
            System.out.print(pop.get(0).genes.get(i) + ", ");
        }
        System.out.print(pop.get(0).genes.get(pop.get(0).genes.size() - 1) + "]");
        System.out.print(", fit/best: (" + pop.get(0).fitness + "/" + pop.get(0).genes.size() + ")");
        System.out.print(", fit/best: (" + pop.get(0).fitness + "/" + pop.get(0).genes.size() + ")");
        System.out.println();

    }
}

class Individual implements Comparable<Individual> {
    List<Integer> genes = new ArrayList<>();
    int fitness = 0;


    Individual() {}

    Individual(int nbits) {
        for (int i = 0; i < nbits; i++) {
            genes.add(ThreadLocalRandom.current().nextInt(0, 2));
        }
    }

    void calculateFitness(int[] target) {
        fitness = 0;
        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i) == target[i]) {
                fitness++;
            }
        }
    }

    @Override
    public int compareTo(Individual individual_2) {
        if (fitness < individual_2.fitness) {
            return 1;
        } else if (fitness == individual_2.fitness) {
            return 0;
        }
        return -1;
    }

    Individual breed(Individual f) {

        Individual d = new Individual();
        List<Integer> gene_x = this.genes;
        List<Integer> gene_y = f.genes;

        for (int i = 0;  i < gene_x.size(); i++) {
            if (Math.random() < 0.5) {
                d.genes.add(gene_x.get(i));
            } else {
                d.genes.add(gene_y.get(i));
            }

            // Mutation
            if (Math.random() < 0.1) {
                d.genes.set(i, (d.genes.get(i) + 1) % 2);
            }


        }
        return d;
    }
}