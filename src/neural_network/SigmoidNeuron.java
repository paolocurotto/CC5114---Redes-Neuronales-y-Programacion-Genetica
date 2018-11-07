package neural_network;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SigmoidNeuron {

    private final double learningRate = 0.01;
    private ArrayList<Double> weights = new ArrayList();
    private double threshold;
    private double delta;
    private double output;


    public SigmoidNeuron(int n_of_weights) {

        double range_w = 0.9;
        double range_t = 0.3;

        // Set random weights
        for (int n = 0; n < n_of_weights; n++) {
            weights.add(ThreadLocalRandom.current().nextDouble(-range_w, range_w));
            //weights.add(ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
        }

        // Set random threshold
        threshold =  ThreadLocalRandom.current().nextDouble(-range_t, range_t);
        //threshold =  ThreadLocalRandom.current().nextDouble(-0.1, 0.1);
    }

    public void feed(ArrayList<Double> inputs) {

        // Check sizes
        /*if (inputs.size() != weights.size())
            System.err.println("n inputs "+inputs.size()+" != n weights " + weights.size());*/

        double z = 0;
        for (int i = 0; i < inputs.size(); i++) {
            z = z + (inputs.get(i) * weights.get(i));
        }

        z = z + threshold;

        double sigmoid = 1 / (1 + Math.exp(-z));

        setOutput(sigmoid);

    }

    ArrayList<Double> getWeights() { return weights; }
    double getLearningRate() { return  learningRate; }
    double getOutput() { return output; }
    void setOutput(double o) { this.output = o; }
    double getDelta() { return  delta; }
    void setDelta(double d) { this.delta = d; }
    double getBias() { return threshold; }
    void setBias(double b) { threshold = b; }
}
