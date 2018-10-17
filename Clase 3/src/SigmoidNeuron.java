import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SigmoidNeuron {

    public double learningRate = 0.5;
    private ArrayList<Double> weights;
    private double threshold;
    private double delta;
    private double output;


    public SigmoidNeuron(int n_of_weights) {

        weights = new ArrayList<>();

        // Set random weights
        for (int n = 0; n < n_of_weights; n++) {
            weights.add(ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
        }

        // Set random threshhold
        threshold =  ThreadLocalRandom.current().nextDouble(-0.2, 0.2);
    }

    public double feed(ArrayList<Double> inputs) {

        // Check sizes
        if (inputs.size() != weights.size())
            System.err.println("n inputs "+inputs.size()+" != n weights " + weights.size());

        double z = 0;
        for (int i = 0; i < inputs.size(); i++) {
            z = z + (inputs.get(i) * weights.get(i));
        }

        z = z - threshold;

        double sigmoid = (double) 1 / (1 + Math.exp(z*-1));

        setOutput(sigmoid);

        return sigmoid;
    }

    public ArrayList<Double> getWeights() { return weights; }
    public void setWeight(int index, double value) {weights.set(index, value); }
    public double getOutput() { return output; }
    public void setOutput(double o) { this.output = o; }
    public double getDelta() { return  delta; }
    public void setDelta(double d) { this.delta = d; }
    public double getBias() { return threshold; }
    public void setBias(double b) { threshold = b; }
}
