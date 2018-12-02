package genetic_algorithm;

import tarea2.Charts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class NQueens {

    int N ; // Queens
    int pop_size; // Population size
    int k; // For tournament selection
    Board board;
    Charts chart;

    public NQueens(int nQueens, int populationSize, int kconts, Charts lineChart) {
        N = nQueens;
        pop_size = populationSize;
        k = kconts;
        chart = lineChart;
    }

    public void start() {

        List<Individual> population = new ArrayList<>(); // Population's individuals list
        List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
        int generationCounter = 0; // To count generations
        int fitnessTarget = N*(N-1)/2; // Best fitness possible

        // Initialize board
        board = new Board(N);

        // Initialize population
        IntStream.range(0, pop_size).forEach(i -> population.add(new Individual(N)));

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(fitnessTarget));
        population.sort((a, b) ->  b.fitness - a.fitness);

        // Solution found?
        boolean solutionFound = (population.get(0).fitness == fitnessTarget);

        // Print info
        System.out.println("Generation: " + generationCounter + ", best/target: (" + population.get(0).fitness + "/" + fitnessTarget + ")");

        // Add values to chart
        if (chart != null) {
            chart.addValueBestFitness(generationCounter, population.get(0).fitness, fitnessTarget);
            double average_t = 0; for (Individual ind : population) { average_t += ind.fitness;}
            average_t=average_t / pop_size;
            chart.addValueAverageFitness(generationCounter, average_t, fitnessTarget);
            chart.addValueWorstFitness(generationCounter, population.get(pop_size - 1).fitness, fitnessTarget);
        }

        /** Genetic algorithm **/
        while (solutionFound == false) {

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

            // Solution found?
            solutionFound = (population.get(0).fitness == fitnessTarget) || (generationCounter > 100000);

            // Print info
            System.out.println("Generation: " + ++generationCounter + ", best/target: (" + population.get(0).fitness + "/" + fitnessTarget + ")");

            // Add values to chart
            if (chart != null) {
                double average_t = 0; for (Individual ind : population) { average_t += ind.fitness;} average_t/=pop_size;
                if (generationCounter % 1 == 0) {
                    chart.addValueWorstFitness(generationCounter, population.get(pop_size - 1).fitness, fitnessTarget);
                    chart.addValueAverageFitness(generationCounter, average_t, fitnessTarget);
                }
                chart.addValueBestFitness(generationCounter, population.get(0).fitness, fitnessTarget);
            }
        }

        // Print solution
        System.out.println();
        for (int i = 0; i < N; i++) { System.out.print(" _"); } System.out.println();
        for (int i = 0; i < N; i++) { System.out.print("|");
            for (int x = 0; x < N; x++) { if (population.get(0).genes.get(i) == x) { System.out.print("X|");}
                else { System.out.print("_|"); } } System.out.println("");
        }
    }



    /**
     * Individual
     * */
    class Individual {
        List<Integer> genes = new ArrayList<>(); // Genes represent tiles on the board
        List<Tile> tiles = new ArrayList<>(); // The actual tiles reference
        int fitness = 0;

        private Individual() {}

        // Initialize individual with random genes
        private Individual(int nQueens) {
            List<Integer> positions = new ArrayList<>();
            IntStream.range(0, nQueens).forEach(positions::add);
            Collections.shuffle(positions);
            for (int r = 0; r < nQueens; r++) {
                int p = positions.get(r);
                genes.add(p);
                tiles.add(board.rows.get(r + 1).get(p + 1));
            }
        }

        // Evaluate fitness
        void calculateFitness(int target) {
            tiles.forEach(t -> t.queenHere = 1);
            int collisions = 0;
            for (Tile t : tiles) { // Check collisions
                collisions += t.queenOn_BL() + t.queenOn_BR();
            }
            tiles.forEach(t -> t.queenHere = 0);
            fitness = target - collisions;
        }

        // Individual 1 + Individual 2 = baby
        Individual breed(Individual f) {
            Individual child = new Individual();
            List<Integer> chromosome_x = new ArrayList<>(this.genes);
            List<Integer> chromosome_y = new ArrayList<>(f.genes);
            int nGenes = chromosome_y.size();

            // Mutation, 10% chance
            if (Math.random() < 0.2) { Collections.swap(chromosome_x,
                        ThreadLocalRandom.current().nextInt(0, nGenes),
                        ThreadLocalRandom.current().nextInt(0, nGenes));
            }
            if (Math.random() < 0.2) { Collections.swap(chromosome_y,
                        ThreadLocalRandom.current().nextInt(0, nGenes),
                        ThreadLocalRandom.current().nextInt(0, nGenes));
            }

            // Make child with N genes from parents' chromosomes at random
            for (int c = 0; c < nGenes; c++) {
                int gene;
                // Pick fathers gene, 50% chance
                if (Math.random() < 0.5) {
                    gene = chromosome_x.get(0);
                }
                // Pick mothers gene, 50% chance
                else {
                    gene = chromosome_y.get(0);
                }
                child.genes.add(gene);
                chromosome_x.remove(Integer.valueOf(gene));
                chromosome_y.remove(Integer.valueOf(gene));
            }
            // Map genes to tiles
            for (int r = 0; r < child.genes.size(); r++) {
                int p = child.genes.get(r);
                child.tiles.add(board.rows.get(r + 1).get(p + 1));
            }
            return child;
        }
    }


    /**
     * Board
     * */
    class Board {
        List<List<Tile>> rows = new ArrayList<>();

        Board(int N) {
            // Initialize tiles
            for (int y = 0; y < N + 2; y++) { // Initialize rows and fill with nulls
                List<Tile> r = new ArrayList<>();
                for (int x = 0; x < N + 2; x++) { r.add(new NullTile()); }
                rows.add(r);
            }
            for (int y = 1; y < N + 1; y++) { // Add tiles in the center to leave null edges
                for (int x = 1; x < N + 1; x++) {
                    rows.get(y).set(x, new Tile());
                }
            }
            // Connect tiles
            for (int y = 1; y < N + 1; y++) {
                for (int x = 1; x < N + 1; x++) {
                    rows.get(y).get(x).BR = rows.get(y + 1).get(x + 1);
                    rows.get(y).get(x).BL = rows.get(y + 1).get(x - 1);
                }
            }
        }
    }


    /**
     * Tile of board
     * */
    class Tile {
        Tile BR;
        Tile BL;
        int queenHere = 0;

        int queenOn_BR() { return BR.queenOn_BR() + BR.queenHere ; }
        int queenOn_BL() { return BL.queenOn_BL() + BL.queenHere ; }
    }

    /**
     * Null Tile
     * */
    class NullTile extends Tile {
        int queenOn_BR() { return 0; }
        int queenOn_BL() { return 0; }
    }

}

