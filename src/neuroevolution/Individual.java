package neuroevolution;

import neural_network.NeuralNetwork;
import neural_network.SigmoidNeuron;
import neuroevolution.pong.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Individual implements Options {
    NeuralNetwork brain;
    List<Integer> genes = new ArrayList<>();
    int fitness = 0;
    int lives = 10;

    // Initialize individual with random genes
    Individual() {
        brain = new NeuralNetwork(new int[]{5, 10, 3});
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
        int index_b = ThreadLocalRandom.current().nextInt(index_a,  nGenes);
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
        List<Double> outputs = Arrays.stream(info).boxed().collect(Collectors.toList());
        double vy = outputs.get(4);
        // Normalize
        double max_vy = BALL_SPEED * Math.sin(1.0471);
        double min_vy = -max_vy;
        double max_vx = BALL_SPEED*Math.cos(0.350);
        double min_vx = -max_vx;
        outputs.set(0, (outputs.get(0) - PADDLE_HEIGHT/2.0) / ((WINDOW_HEIGHT - PADDLE_HEIGHT/2.0) - PADDLE_HEIGHT/2.0));
        outputs.set(1, (outputs.get(1) - 0) / (WINDOW_WIDTH - 0));
        outputs.set(2, (outputs.get(2) - 0) / (WINDOW_HEIGHT - 0));
        outputs.set(3, (outputs.get(3) - min_vx) / (max_vx - min_vx));
        outputs.set(4, (outputs.get(4) - min_vy) / (max_vy - min_vy));

        for (int i = 0; i < outputs.size(); i++) {
            double d = outputs.get(i);
            if (d < 0 || d > 1) {
                if (i != 1) {
                    System.out.println("Error at " + i + ", d=" + d + ", vy = " + vy);
                }
            }
        }

        return brain.evaluate_index(outputs);
    }
}
