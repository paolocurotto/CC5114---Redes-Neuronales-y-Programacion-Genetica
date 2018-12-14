package neuroevolution;

import neural_network.NeuralNetwork;
import neural_network.SigmoidNeuron;
import neuroevolution.pong.Ball;
import neuroevolution.pong.Paddle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
    public void calculateFitness(int hits) {
        fitness = hits;
    }

    // Individual 1 + Individual 2 = baby
    Individual breed(Individual f) {
        Individual child = new Individual();
        int nGenes = this.brain.n_neurons;

        int index_a = ThreadLocalRandom.current().nextInt(0, nGenes);
        int index_b = ThreadLocalRandom.current().nextInt(index_a + 1, nGenes + 1);
        int current = 0;
        for (int x = 0; x < child.brain.neuralLayers.size(); x++) {

            for (int y = 0; y < child.brain.neuralLayers.get(x).neurons.size(); y++) {

                // Pick from mother
                if (current <= index_b && index_a <= current) {
                    SigmoidNeuron hNeuron = f.brain.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.brain.neuralLayers.get(x).neurons.set(y, hNeuron);
                }

                // Pick from father
                else {
                    SigmoidNeuron hNeuron = this.brain.neuralLayers.get(x).neurons.get(y).makeClone();
                    child.brain.neuralLayers.get(x).neurons.set(y, hNeuron);
                }

                current++;
            }

        }



        return child;
    }

    public int movePaddle(double[] info) {
        List<Double> outputs =Arrays.stream(info).boxed().collect(Collectors.toList());
        int dir = brain.evaluate_index(outputs);
        return dir;
    }
}
