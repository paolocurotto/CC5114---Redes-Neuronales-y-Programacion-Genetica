package genetic_algorithm;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BitsSequence {

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

    public static int start() {

        // Population size
        int N = 50;
        // Secret bits sequence
        int[] secretSeq = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        };
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

        //print_info(generationCounter, population);


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
            if (population.get(0).fitness == n_bits || generationCounter > 50000) {
                solutionFound = true;
            }
            ++generationCounter;
            //print_info(++generationCounter, population);

        }
        print_info(generationCounter, population);
        System.out.println("Finished.");
        if (generationCounter > 20000) {System.out.println("GENERATIONS > 20000.");}
        return generationCounter;

    }


    static void print_info(int c, List<Individual> pop) {
        System.out.print("Generation: " + c + ", best: [");
        for (int i = 0; i < pop.get(0).genes.size() - 1; i++) {
            System.out.print(pop.get(0).genes.get(i) + ", ");
        }
        System.out.print(pop.get(0).genes.get(pop.get(0).genes.size() - 1) + "]");
        System.out.print(", fit/best: (" + pop.get(0).fitness + "/" + pop.get(0).genes.size() + ")");
        System.out.print(", fit/best: (" + pop.get(0).fitness + "/" + pop.get(0).genes.size() + ")");
        System.out.println();

    }





    static class Individual implements Comparable<Individual> {
        List<Integer> genes = new ArrayList<>();
        int fitness = 0;


        private Individual() {}

        private Individual(int nbits) {
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
                if (Math.random() < 0.05) {
                    d.genes.set(i, (d.genes.get(i) + 1) % 2);
                }
            }
            return d;
        }
    }

}

