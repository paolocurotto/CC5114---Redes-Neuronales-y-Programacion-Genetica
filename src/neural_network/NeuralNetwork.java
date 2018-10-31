package neural_network;

import utils.GraphPane;
import java.util.ArrayList;

import static neural_network.DataValue.*;

public class NeuralNetwork {

    private ArrayList<NeuronLayer> neuralLayers = new ArrayList<>();


    // layers[0] -> number of inputs
    // layers[1 .. n] -> number of neurons of layer n
    public NeuralNetwork(int[] layers) {
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

        // Normalize data set
        normalizeDataset(dataset);

        // Iterate number of epochs times
        for (int epoch = 0; epoch < nOfEpochs; epoch++) {

            double mean_squared_error = 0;
            int true_positive_count = 0;
            int false_positive_count = 0;

            // Iterate through data set
            for (DataValue data : dataset) {

                // Get inputs
                ArrayList<Double> inputs = data.inputs;

                // Get desired outputs
                ArrayList<Double> desiredOutputs = data.desiredOutputs;

                // Train
                trainNetwork(inputs, desiredOutputs);

                // Get outputs
                ArrayList<Double> outputs = neuralLayers.get(neuralLayers.size() - 1).getOutputs();

               /*
               * Calculate Info
               * */

                // Check prediction
                switch (checkAnswer(outputs, desiredOutputs)) {
                    case TRUE_POSITIVE:
                        true_positive_count++;
                        break;

                    case FALSE_POSITIVE:
                        false_positive_count++;
                        break;
                }

                // Mean_squared_error of this input
                double input_error = 0;
                for (int k = 0; k < desiredOutputs.size(); k++) {
                    input_error += Math.pow(desiredOutputs.get(k) - outputs.get(k), 2);
                }

                // Add to overall error of epoch
                mean_squared_error += input_error;
            }

            /*
             * After epoch of training
             * */

            // Calculate mean squared error and precision of epoch
            mean_squared_error = mean_squared_error / dataset.size();
            double precision = (double) true_positive_count / (true_positive_count + false_positive_count);

            // Add values to line charts
            graphPane.addValueMSE(epoch, mean_squared_error);
            graphPane.addValuePrecision(epoch, precision);

        }

    }

}
