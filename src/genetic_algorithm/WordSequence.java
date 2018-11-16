package genetic_algorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WordSequence {

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        int N = 50; // Population size
        String secretWord = "GENETICALGORITHM"; // Secret word
        int n_letters = secretWord.length(); // Secret word's number of letters
        int generationCounter = 0; // To count generations

        List<Individual> population = new ArrayList<>();
        List<Individual> new_generation = new ArrayList<>();
        List<Individual> temp;

        // Initialize population
        for (int i = 0; i < N; i++) {
            population.add(new Individual(n_letters));
        }

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(secretWord));
        population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

        // Solution found?
        boolean solutionFound = (population.get(0).fitness == n_letters);

        // Print info
        print_info(generationCounter, population);


        /** Genetic algorithm **/
        while (solutionFound == false) {

            // Selection
            int selectionAmount = N / 5;
            List<Individual> bestIndividuals = population.subList(0, selectionAmount);

            // Reproduction
            for (int i = 0; i < N; i++) {
                Individual m = bestIndividuals.get(ThreadLocalRandom.current().nextInt(0, selectionAmount));
                Individual f = bestIndividuals.get(ThreadLocalRandom.current().nextInt(0, selectionAmount));
                while (m.equals(f) == true) {
                    f = population.get(ThreadLocalRandom.current().nextInt(0, selectionAmount));
                }

                Individual new_individual = m.breed(f);
                new_generation.add(new_individual);
            }
            temp = population;
            population = new_generation;
            new_generation = temp;
            new_generation.clear();

            // Evaluate fitness
            population.forEach(individual -> individual.calculateFitness(secretWord));
            population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

            // Solution found?
            solutionFound = (population.get(0).fitness == n_letters);

            // Print info
            print_info(++generationCounter, population);
        }
        System.out.println("Finished.");
    }

    static void print_info(int c, List<Individual> pop) {
        System.out.print("Generation: " + c + ", best: [");
        for (int i = 0; i < pop.get(0).genes.length() - 1; i++) {
            System.out.print(pop.get(0).genes.charAt(i) + ", ");
        }
        System.out.print(pop.get(0).genes.charAt(pop.get(0).genes.length() - 1) + "]");
        System.out.println(", fit/best: (" + pop.get(0).fitness + "/" + pop.get(0).genes.length() + ")");
    }




    /**
     * Individual
     * */
    //static class Individual implements Comparable<Individual> {
    static class Individual {
        String genes = "";
        int fitness = 0;

        private Individual() {
        }

        private Individual(int nletters) {
            for (int i = 0; i < nletters; i++) {
                genes = genes + Letter.randomLetter();
            }
        }

        void calculateFitness(String word_target) {
            fitness = 0;
            for (int i = 0; i < genes.length(); i++) {
                if (genes.charAt(i) == word_target.charAt(i)) {
                    fitness++;
                }
            }
        }

        Individual breed(Individual f) {
            Individual child = new Individual();
            String gene_x = this.genes;
            String gene_y = f.genes;
            for (int i = 0;  i < gene_x.length(); i++) {
                // Mutation, 10% chance
                if (Math.random() < 0.1) {
                    child.genes = child.genes + Letter.randomLetter();
                }
                // Pick father gene, 50% change
                else if (Math.random() < 0.5) {
                    child.genes = child.genes + gene_x.charAt(i);
                }
                // Pick mother gene, 50% chance
                else {
                    child.genes = child.genes + gene_y.charAt(i);
                }
            }
            return child;
        }
    }


    private enum Letter {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

        private static final Letter[] VALUES = values();
        private static final int SIZE = VALUES.length;
        private static final Random RANDOM = new Random();
        public static Letter randomLetter()  {
            return VALUES[RANDOM.nextInt(SIZE)];
        }
    }

}

