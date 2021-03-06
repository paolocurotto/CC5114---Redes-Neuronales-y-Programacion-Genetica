package genetic_algorithm;

import javax.sound.sampled.Line;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class WordSequence {

    public static void main(String[] args) {

        int n;
        ArrayList<Double> times = new ArrayList<>();
        ArrayList<Double> gens = new ArrayList<>();
        for (n = 0; n < 50; n++) {
            Instant start = Instant.now();
            int n_gen = start();
            Instant finish = Instant.now();
            times.add((double) Duration.between(start, finish).toMillis());
            gens.add((double) n_gen);
        }
        //1
        double average_t = times.stream().mapToDouble(val -> val).average().orElse(0.0);
        double average_gens = gens.stream().mapToDouble(val -> val).average().orElse(0.0);
        //2
        times.replaceAll(i -> average_t - i);
        gens.replaceAll(i -> average_gens - i);
        //3
        times.replaceAll(i -> i*i);
        gens.replaceAll(i -> i*i);
        //4
        double sum_t = 0; for (double d : times) {sum_t+=d;}
        double sum_gens = 0; for (double d : gens) {sum_gens+=d;}
        //5 & 6
        double s_times =  Math.sqrt(sum_t/(times.size()-1));
        double s_gens =  Math.sqrt(sum_gens/(gens.size()-1));

        System.out.println("Avg_T/S = " + average_t + " ms, " + s_times);
        System.out.println("Avg_GENS/S = " + average_gens + " generations, " + s_gens);
    }

    private static int start() {

        int N = 100; // Population size
        String secretWord = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC"; // Secret word
        int n_letters = secretWord.length(); // Secret word's number of letters
        int generationCounter = 0; // To count generations
        List<Individual> population = new ArrayList<>(); // Population's individuals list
        List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
        int k = N; // For tournament selection

        // Initialize population
        IntStream.range(0, N).forEach(i -> population.add(new Individual(n_letters)));

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(secretWord));
        population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

        // Solution found?
        boolean solutionFound = (population.get(0).fitness == n_letters);

        // Print info
        //print_info(generationCounter, population);


        /** Genetic algorithm **/
        while (solutionFound == false) {


            // Selection
            /*
            int selectionAmount = N / 5;
            List<Individual> bestIndividuals = population.subList(0, selectionAmount);

            // Reproduction
            for (int i = 0; i < N; i++) {
                int father_index = ThreadLocalRandom.current().nextInt(0, selectionAmount);
                int mother_index = (father_index + ThreadLocalRandom.current().nextInt(1, selectionAmount)) % selectionAmount;
                Individual father = bestIndividuals.get(father_index);
                Individual mother = bestIndividuals.get(mother_index);
                Individual baby = father.breed(mother);
                new_generation.add(baby);
            }*/



            // Tournament selection and reproduction
            for (int n = 0; n < N; n++) {
                int father_index = -1; // Find dad
                for (int i = 0; i < k; i++) {
                    int best_index = ThreadLocalRandom.current().nextInt(0, N);
                    if (father_index == -1 || population.get(best_index).fitness > population.get(father_index).fitness) {
                        father_index = best_index;
                    }
                }
                int mother_index = -1; // Find mom
                for (int i = 0; i < k; i++) {
                    int best_index = (father_index + ThreadLocalRandom.current().nextInt(1, N)) % N;
                    if (i == 0 || population.get(best_index).fitness > population.get(mother_index).fitness) {
                        mother_index = best_index;
                    }
                }
                Individual baby = population.get(father_index).breed(population.get(mother_index));
                new_generation.add(baby);
            } // Tournament selection end


            // Set offspring as the current generation and kill old generation
            population.clear();
            population.addAll(new_generation);
            new_generation.clear();

            // Evaluate fitness
            population.forEach(individual -> individual.calculateFitness(secretWord));
            population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

            // Solution found?
            solutionFound = (population.get(0).fitness == n_letters);

            // Print info
            //print_info(++generationCounter, population);
            ++generationCounter;
        }
        print_info(generationCounter, population);
        return generationCounter;
    }

    static void print_info(int c, List<Individual> pop) {
        String genes = pop.get(0).genes;
        System.out.print("Generation: " + c + ", best: [");
        genes.chars().mapToObj(i -> (char) i).forEach(System.out::print);
        System.out.println("], best/target: (" + pop.get(0).fitness + "/" + genes.length() + ")");
    }



    /**
     * Individual
     * */
    static class Individual {
        String genes = "";
        int fitness = 0;

        private Individual() {
        }

        private Individual(int nletters) {
            IntStream.range(0, nletters).forEach(i -> genes = genes + Letter.randomLetter());
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
                // Pick father gene, 50% chance
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
        private static final Random RANDOM = new Random(24);
        public static Letter randomLetter()  {
            return VALUES[RANDOM.nextInt(SIZE)];
        }
    }

}

