package neuroevolution;

import neural_network.NeuralNetwork;
import neuroevolution.pong.Ball;
import neuroevolution.pong.Paddle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Individual {
    NeuralNetwork brain;
    List<Integer> genes = new ArrayList<>(); // Genes represent tiles on the board
    int fitness = 0;

    // Initialize individual with random genes
    Individual() {
        brain = new NeuralNetwork(new int[]{5, 3, 4, 3});
    }

    /*
    *  output 0 -> UP
    *  output 1 -> IDLE
    *  output 2 -> DOWN
    * */

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

    public int movePaddle(double[] info) {
        List<Double> outputs =Arrays.stream(info).boxed().collect(Collectors.toList());
        int dir = brain.evaluate_index(outputs);
        return dir;
    }
}
