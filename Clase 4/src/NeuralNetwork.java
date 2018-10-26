import java.lang.reflect.Array;
import java.util.ArrayList;

public class NeuralNetwork {

    private ArrayList<NeuronLayer> neuralLayers = new ArrayList<>();
    public int epoch = 0;
    public int identifier;

    public NeuralNetwork(int[] layers) {

        // layers[0] -> number of inputs
        // layers[1 .. n] -> number of neurons of layer n
        for (int n = 1; n < layers.length; n++) {

            neuralLayers.add(new NeuronLayer(layers[n], layers[n - 1]));

            if (neuralLayers.size() >= 2) {
                neuralLayers.get(n - 2).setNextLayer(neuralLayers.get(n - 1));
                neuralLayers.get(n - 1).setPreviousLayer(neuralLayers.get(n - 2));
            }
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



    public ArrayList<Double> evaluate(ArrayList<Double> inputs) {
        neuralLayers.get(0).feedLayer(inputs);
        return neuralLayers.get(neuralLayers.size() - 1).getOutputs();
    }

    public void trainNetworkWithEpochs(ArrayList<DataValue> dataset, int nOfEpochs, GraphPane graphPane) {

        // Iterate number of epochs times
        for (int i = 0; i < nOfEpochs; i++) {

            double mean_squared_error = 0;
            int true_positive_count = 0;
            int false_positive_count = 0;

            // Iterate through data set
            for (DataValue data : dataset) {

                // Get inputs
                ArrayList<Double> inputs = data.inputs;

                // Get desired outputs
                ArrayList<Double> desiredOutputs = data.desiredOutputs;

                // Training
                neuralLayers.get(0).feedLayer(inputs);
                neuralLayers.get(neuralLayers.size() - 1).backwardPropagate(desiredOutputs);
                neuralLayers.get(0).updateWeights(inputs);

                // Get outputs
                ArrayList<Double> outputs = neuralLayers.get(neuralLayers.size() - 1).getOutputs();

                // Calculate Info

                //mean_squared_error
                double input_error = 0;
                for (int k = 0; k < desiredOutputs.size(); k++) {

                    input_error += Math.pow(desiredOutputs.get(k) - outputs.get(k), 2);
                }

                mean_squared_error += input_error;
            }

            // After epoch of training
            mean_squared_error = (double) mean_squared_error / dataset.size();

            graphPane.addValue(epoch++, mean_squared_error, identifier);
        }
    }



}
