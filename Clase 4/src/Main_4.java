import java.util.ArrayList;

public class Main_4 {

    public static void main(String[] args) {

        int[] layers = new int[] {5, 3, 4, 1};

        NeuralNetwork neuralNetwork = new NeuralNetwork(layers);

        ArrayList<Double> inputs = new ArrayList<>();
        inputs.add((double) 5);
        inputs.add((double) 1);
        inputs.add((double) 3);
        inputs.add((double) 4);
        inputs.add((double) 2);

        ArrayList<Double> desiredOutput = new ArrayList<>();
        desiredOutput.add((double) 1);

        neuralNetwork.trainNetwork(inputs, desiredOutput);

    }
}
