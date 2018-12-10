package neuroevolution;

import neural_network.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    NeuralNetwork brain;
    List<Integer> genes = new ArrayList<>(); // Genes represent tiles on the board
    int fitness = 0;

    // Initialize individual with random genes
    Individual() {
        brain = new NeuralNetwork(new int[]{5, 3, 4, 3});
    }

    void thinkOutput() {

    }

    // Evaluate fitness
    void calculateFitness(int target) {

    }

    // Individual 1 + Individual 2 = baby
    Individual breed(Individual f) {
        Individual child = new Individual();
        List<Integer> chromosome_x = new ArrayList<>(this.genes);
        List<Integer> chromosome_y = new ArrayList<>(f.genes);
        int nGenes = chromosome_y.size();

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

        // Mutation, 10% chance
        if (Math.random() < 0.2) {

        }

        return child;
    }
}
