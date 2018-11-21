package genetic_algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class TravellingSalesman {

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

        // Stop condition?
        boolean stop = false;

        // Print info
        //print_info(generationCounter, population);

        /* Genetic algorithm */
        while(stop == false) {





        }
    }



    private static void initialize_graph(int number_of_nodes) {
        for (int i = 1; i <= number_of_nodes; i++) {
            NodeType nodeType;
            switch (i) {

                case 0:
                    graph.add(new Node(i, nodeType));
                    break;
                case (number_of_nodes):
                    graph.add(new Node(i, nodeType));
                    break;
                default:
                    graph.add(new Node(i, NodeType.NORMAL));



            }

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
        int identifier;
        NodeType nodeType;
        List<Node> connections = new ArrayList<>();
        Node(int i, NodeType t) {
           identifier = i;
           nodeType = t;
        }
    }

    enum NodeType {
        START, NORMAL, END
    }
}
