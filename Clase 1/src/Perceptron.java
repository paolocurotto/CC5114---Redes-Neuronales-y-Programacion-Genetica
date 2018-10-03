public abstract class Perceptron {

    public int[] inputs;
    public int[] weights;
    public int threshold;

    public Perceptron(int[] weights, int bias) {
        this.inputs = new int[weights.length];
        this.weights = weights;
        this.threshold = bias;
    }

    public int calc() {
        int result = 0;
        for (int i = 0; i < inputs.length; i++) {
            result = result + inputs[i] * weights[i];
        }

        if (result > threshold)
            return 1;
        else
            return 0;
    }

    public int eval(int x1, int x2){
        return -1;
    }
}
