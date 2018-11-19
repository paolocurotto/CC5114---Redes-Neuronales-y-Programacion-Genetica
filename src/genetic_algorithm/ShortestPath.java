package genetic_algorithm;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ShortestPath {

    static int N = 50; // Population size
    static int generationCounter = 0; // To count generations
    static List<Individual> population = new ArrayList<>();
    static List<Node> graph = new ArrayList<>();
    static int number_of_nodes = 100;

    public static void main(String[] args) {

        // Initialize graph
        initialize_graph(number_of_nodes);

        // Initialize population
        IntStream.range(0, N).forEach(i -> population.add(new Individual()));

        // Evaluate fitness
        population.forEach(individual -> individual.calculateFitness(graph));
        population.sort((ind_1, ind_2) ->  ind_2.fitness - ind_1.fitness);

        // Solution found?
        //boolean solutionFound = (population.get(0).fitness == n_letters);

        // Print info
        //print_info(generationCounter, population);
    }



    private static void initialize_graph(int number_of_nodes) {
        for (int i = 0; i < number_of_nodes; i++) {
            graph.add(new Node());
        }
        for (int i = 0; i < number_of_nodes - 1; i++) {
            for (int rest = i + 1; rest < number_of_nodes; rest++) {
                if (Math.random() < 0.5) { // Connect node(i) <-> node(rest)
                    graph.get(i).connections.add(graph.get(rest));
                    graph.get(rest).connections.add(graph.get(i));
                }
            }
        }
    }


    /**
     * Individual
     */
    static class Individual {
        List<Integer> genes = new ArrayList<>();
        int fitness = 0;
        int genesSize = 20;
        int current = 0;
        public Individual() {
            IntStream.range(0, genesSize).forEach(
                    i -> genes.add(ThreadLocalRandom.current().nextInt(0, graph.size() - 1)));
        }

        void calculateFitness(List<Node> g) {
            fitness = 0;

        }

    }






    /**
     * Node of a graph
     * */
    static class Node {
        List<Node> connections = new ArrayList<>();
    }
}
