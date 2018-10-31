package perceptrons;

public class Perceptron {

    public double[] inputs;
    public double[] weights;
    public double threshold;

    public Perceptron(double[] weights, double bias) {
        this.inputs = new double[weights.length];
        this.weights = weights;
        this.threshold = bias;
    }

    public int calc() {
        double result = 0;
        for (int i = 0; i < inputs.length; i++) {
            result = result + (inputs[i] * weights[i]);
        }

        if (result > threshold)
            return 1;
        else
            return 0;
    }

    public int eval(int x1, int x2){
        return -1;
    }

    public void set2Inputs(double x1, double x2) {
        inputs[0] = x1;
        inputs[1] = x2;
    }

    public void train(double[] input, int desired) {

        inputs = input;
        int output = calc();
        // Learning algorithm
        int diff = desired - output;
        double lr = 0.7;

        for (int i = 0; i < inputs.length; i++) {
            weights[i] += lr * inputs[i] * diff;
        }

        threshold = threshold + (lr * diff);
    }
}
