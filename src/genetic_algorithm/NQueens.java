package genetic_algorithm;

import java.util.*;
import java.util.stream.IntStream;

public class NQueens {

    static Board board;

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        int pop_size = 50; // Population size
        int N = 4; // Queens
        int generationCounter = 0; // To count generations
        List<Individual> population = new ArrayList<>(); // Population's individuals list
        List<Individual> new_generation = new ArrayList<>(); // Population's offspring generation
        int k = 30; // For tournament selection


        // Initialize board
        board = new Board(N);

        // Initialize population
        IntStream.range(0, pop_size).forEach(i -> population.add(new Individual(i, N)));

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(N));
        population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

        // Solution found?
        boolean solutionFound = (population.get(0).fitness == N);

        // Print info
        print_info(generationCounter, population);


        /** Genetic algorithm **/
        /*
        while (solutionFound == false) {


            // Selection
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
            }



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
                //Individual baby = population.get(father_index).breed(population.get(mother_index));
                new_generation.add(baby);
            } // Tournament selection end


            // Set offspring as the current generation and kill old generation
            population.clear();
            population.addAll(new_generation);
            new_generation.clear();

            // Evaluate fitness
            population.forEach(individual -> individual.calculateFitness(N));
            population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

            // Solution found?
            solutionFound = (population.get(0).fitness == N);

            // Print info
            print_info(++generationCounter, population);
        }

        */
    }

    static void print_info(int c, List<Individual> pop) {
        //String genes = pop.get(0).genes;
        //System.out.print("Generation: " + c + ", best: [");
        //genes.chars().mapToObj(i -> (char) i).forEach(System.out::print);
        //System.out.println("], best/target: (" + pop.get(0).fitness + "/" + genes.length() + ")");
    }



    /**
     * Individual
     * */
    static class Individual {
        List<Tile> genes = new ArrayList<>();
        int fitness = 0;
        int identifier;

        private Individual(int iden, int nQueens) {
            identifier = iden;
            List<Integer> positions = new ArrayList<>();
            IntStream.range(0, nQueens * nQueens).forEach(positions::add);
            Collections.shuffle(positions);
            for (int i = 0; i < nQueens; i++) {
                int r = 1 + positions.get(i) / nQueens;
                int c = 1 + positions.get(i) % nQueens;
                genes.add(board.rows.get(r).tiles.get(c));
            }
        }

        void calculateFitness(int nQueens) {
            fitness = nQueens;
            int collisions = 0;

            genes.forEach(t -> t.queenHere = 1);

            // Check collisions
            for (Tile t : genes) {
                collisions += t.T.queenOn_T();
                collisions += t.TR.queenOn_TR();
                collisions += t.R.queenOn_R();
                collisions += t.BR.queenOn_BR();
                collisions += t.B.queenOn_B();
                collisions += t.BL.queenOn_BL();
                collisions += t.L.queenOn_L();
                collisions += t.TL.queenOn_TL();
            }

            System.out.println("ind = " + identifier + ", collisions = " + collisions);

            genes.forEach(t -> t.queenHere = 0);
        }

        /*
        Individual breed(Individual f) {

            Individual child = new Individual();
            String gene_x = this.genes;
            String gene_y = f.genes;
            for (int i = 0;  i < gene_x.length(); i++) {
                // Mutation, 10% chance
                if (Math.random() < 0.1) {
                    child.genes = child.genes;
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
        }*/

    }


    /**
     * Queen's board
     * */
    static class Board {
        List<Row> rows = new ArrayList<>();

        Board(int N) {
            // Initialize rows and tiles
            for (int i = 0; i < N + 2; i++) {
                Row r = new Row(i, N);
                rows.add(r);
            }
            // Connect tiles
            for (int y = 1; y < N + 1; y++) {
                for (int x = 1; x < N + 1; x++) {
                    rows.get(y).tiles.get(x).T  = rows.get(y - 1).tiles.get(x);
                    rows.get(y).tiles.get(x).TR = rows.get(y - 1).tiles.get(x + 1);
                    rows.get(y).tiles.get(x).R  = rows.get(y).tiles.get(x + 1);
                    rows.get(y).tiles.get(x).BR = rows.get(y + 1).tiles.get(x + 1);
                    rows.get(y).tiles.get(x).B  = rows.get(y + 1).tiles.get(x);
                    rows.get(y).tiles.get(x).BL = rows.get(y + 1).tiles.get(x - 1);
                    rows.get(y).tiles.get(x).L  = rows.get(y).tiles.get(x - 1);
                    rows.get(y).tiles.get(x).TL = rows.get(y - 1).tiles.get(x - 1);
                }
            }
        }
    }


    /**
     * Tile of board
     * */
    static class Tile {
        Tile TL; Tile T; Tile TR;
        Tile L; /*this*/ Tile R;
        Tile BL; Tile B; Tile BR;

        int queenHere = 0;
        int queenOn_T()  { return queenHere +  T.queenOn_T() ; }
        int queenOn_TR() { return queenHere + TR.queenOn_TR(); }
        int queenOn_R()  { return queenHere +  R.queenOn_R() ; }
        int queenOn_BR() { return queenHere + BR.queenOn_B() ; }
        int queenOn_B()  { return queenHere +  B.queenOn_B() ; }
        int queenOn_BL() { return queenHere + BL.queenOn_BL(); }
        int queenOn_L()  { return queenHere +  L.queenOn_L() ; }
        int queenOn_TL() { return queenHere + TL.queenOn_TL(); }
    }

    /**
     * Null Tile
     * */
    static class NullTile extends Tile {
        int queenOn_T()  { return 0; }
        int queenOn_TR() { return 0; }
        int queenOn_R()  { return 0; }
        int queenOn_BR() { return 0; }
        int queenOn_B()  { return 0; }
        int queenOn_BL() { return 0; }
        int queenOn_L()  { return 0; }
        int queenOn_TL() { return 0; }
    }


    /**
     * Row
     * */
    static class Row {
        List<Tile> tiles = new ArrayList<>();

        Row(int r, int N) {
            for (int i = 0; i < N + 2; i++) {
                if (r == 0) { tiles.add(new NullTile()); } // First row is nulls
                else if (r == N + 1) { tiles.add(new NullTile()); } // Last row is nulls
                else if (i == 0) { tiles.add(new NullTile());} // First column is nulls
                else if (i == N + 1) {tiles.add(new NullTile());} // Last column is nulls
                else { tiles.add(new Tile()); } // Normal board
            }
        }
    }

}

