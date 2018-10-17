import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class NeuralNetwork {

    private ArrayList<NeuronLayer> neuralLayers = new ArrayList<>();

    public NeuralNetwork(int[] layers) {

        // layers[0] -> number of inputs
        // layers[1 .. n] -> number of neurons of layer n
        for (int n = 1; n < layers.length; n++) {

            neuralLayers.add(new NeuronLayer(layers[n], layers[n - 1]));

            if (neuralLayers.size() < 2) {
                continue;
            }

            neuralLayers.get(n - 2).setNextLayer(neuralLayers.get(n - 1));
            neuralLayers.get(n - 1).setPreviousLayer(neuralLayers.get(n - 2));
        }
    }

    public void trainNetwork(ArrayList<Double> inputs, ArrayList<Double> expectedOutput) {

        // Start forward feeding
        neuralLayers.get(0).feedLayer(inputs);

        // Start backward propagation
        neuralLayers.get(neuralLayers.size() - 1).backwardPropagate(expectedOutput);

        // Start weights update
        neuralLayers.get(0).updateWeights(inputs);
    }

    public double evaluate(ArrayList<Double> inputs) {

        neuralLayers.get(0).feedLayer(inputs);

        return neuralLayers.get(neuralLayers.size() - 1).getNeurons().get(0).getOutput();

    }

    public void showWeights1() {

        NeuronLayer layer1 = neuralLayers.get(0);
        int i = 0;
        System.out.println("");
        for (SigmoidNeuron neuron : layer1.getNeurons()) {
            System.out.print("neuron " + i + " : [");

            for (double weight : neuron.getWeights()) {
                System.out.print(weight + ", ");

            }
            i++;
            System.out.println("], bias = " + neuron.getBias());
        }
    }

    public void showWeights2() {

        NeuronLayer layer1 = neuralLayers.get(1);
        int i = 0;
        System.out.println("");
        for (SigmoidNeuron neuron : layer1.getNeurons()) {
            System.out.print("neuron " + i + " : [");

            for (double weight : neuron.getWeights()) {
                System.out.print(weight + ", ");

            }
            i++;
            System.out.println("], bias = " + neuron.getBias());
        }
    }
}
