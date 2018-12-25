package neural_network;

import tarea3.Globals.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static tarea3.Globals.Mutation.*;

public class SigmoidNeuron {

    private double learningRate = 0.5;
    private ArrayList<Double> weights = new ArrayList<>();
    private double threshold;
    private double delta;
    private double output;
    public Mutation mutation = NO_MUTATION;

    SigmoidNeuron() {
    }

    SigmoidNeuron(int n_of_weights) {
        double range = 2;
        // Set random weights
        for (int n = 0; n < n_of_weights; n++) {
            weights.add(ThreadLocalRandom.current().nextDouble(-range, range));
        }

        // Set random threshold
        threshold =  ThreadLocalRandom.current().nextDouble(-range, range);
    }

    void feed(ArrayList<Double> inputs) {
        double z = 0;
        for (int i = 0; i < inputs.size(); i++) {
            z = z + (inputs.get(i) * weights.get(i));
        }
        z = z + threshold;
        this.output = 1 / (1 + Math.exp(-z));
    }

    void feed(double[] inputs) {
        double z = 0;
        for (int i = 0; i < inputs.length; i++) {
            z = z + (inputs[i] * weights.get(i));
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

    public SigmoidNeuron makeClone() {
        SigmoidNeuron s = new SigmoidNeuron();
        Mutation m = NO_MUTATION;
        for (double w : this.weights) {
            double r = Math.random();
            if (r < 0.005) {
                w = -w;
                m = SIGN_INVERT_MUTATION;
            }
            if (r > 0.99) {
                w = w * 1.2;
                m = AMP_MUTATION;
            }
            s.weights.add(w);
        }
        s.threshold = this.threshold;
        s.mutation = m;
        return  s;
    }
}
