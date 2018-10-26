import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SigmoidNeuron {

    private final double learningRate = 5;
    private ArrayList<Double> weights = new ArrayList();
    private double threshold;
    private double delta;
    private double output;


    public SigmoidNeuron(int n_of_weights) {

        // Set random weights
        for (int n = 0; n < n_of_weights; n++) {
            weights.add(ThreadLocalRandom.current().nextDouble(-0.3, 0.3));
        }

        // Set random threshold
        threshold =  ThreadLocalRandom.current().nextDouble(-0.1, 0.1);
    }

    public void feed(ArrayList<Double> inputs) {

        // Check sizes
        if (inputs.size() != weights.size())
            System.err.println("n inputs "+inputs.size()+" != n weights " + weights.size());

        double z = 0;
        for (int i = 0; i < inputs.size(); i++) {
            z = z + (inputs.get(i) * weights.get(i));
        }

        z = z + threshold;

        double sigmoid = 1 / (1 + Math.exp(-z));

        setOutput(sigmoid);

    }

    public ArrayList<Double> getWeights() { return weights; }
    public double getLearningRate() { return  learningRate; }
    public double getOutput() { return output; }
    public void setOutput(double o) { this.output = o; }
    public double getDelta() { return  delta; }
    public void setDelta(double d) { this.delta = d; }
    public double getBias() { return threshold; }
    public void setBias(double b) { threshold = b; }
}
