package neural_network;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SigmoidNeuron {

    private double learningRate = 0.05;
    private ArrayList<Double> weights = new ArrayList<>();
    private double threshold;
    private double delta;
    private double output;


    SigmoidNeuron(int n_of_weights) {
        // Set random weights
        for (int n = 0; n < n_of_weights; n++) {
            weights.add(ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
        }

        // Set random threshold
        threshold =  ThreadLocalRandom.current().nextDouble(-0.2, 0.2);
    }

    void feed(ArrayList<Double> inputs) {
        double z = 0;
        for (int i = 0; i < inputs.size(); i++) {
            z = z + (inputs.get(i) * weights.get(i));
        }
        z = z + threshold;
        this.output = 1 / (1 + Math.exp(-z));
    }

    public ArrayList<Double> getWeights() { return weights; }
    public void setWeights(ArrayList<Double> w) { this.weights = w; }
    double getLearningRate() { return  learningRate; }
    public void setLearningRate(double lr) { this.learningRate = lr; }
    double getOutput() { return output; }
    double getDelta() { return  delta; }
    void setDelta(double d) { this.delta = d; }
    public double getBias() { return threshold; }
    public void setBias(double b) { threshold = b; }
}
