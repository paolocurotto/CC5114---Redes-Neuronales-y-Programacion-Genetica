package neural_network;

import utils_graphs.GraphPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import static neural_network.DataValue.*;

public class NeuralNetwork {

    ArrayList<NeuronLayer> neuralLayers = new ArrayList<>();


    // E.g: layers = [5, 7, 3] -> NN with 5 inputs, 7 neurons in hidden layer, 3 neurons in output
    public NeuralNetwork(int[] layers) {
        for (int n = 1; n < layers.length; n++) {

            // E.g: NeuronLayer(3, 5) -> Layer with 3 neurons and 5 weights per neuron
            neuralLayers.add(new NeuronLayer(layers[n], layers[n - 1]));

            if (neuralLayers.size() >= 2) {
                neuralLayers.get(n - 2).setNextLayer(neuralLayers.get(n - 1));
                neuralLayers.get(n - 1).setPreviousLayer(neuralLayers.get(n - 2));
            }
        }
    }

    public void trainNetwork(ArrayList<Double> inputs, ArrayList<Double> expectedOutputs) {

        // Start forward feeding
        neuralLayers.get(0).feedLayer(inputs);

        // Start backward propagation
        neuralLayers.get(neuralLayers.size() - 1).backwardPropagate(expectedOutputs);

        // Start weights update
        neuralLayers.get(0).updateWeights(inputs);
    }


    public ArrayList<Double> evaluate(ArrayList<Double> inputs) {
        neuralLayers.get(0).feedLayer(inputs);
        return neuralLayers.get(neuralLayers.size() - 1).getOutputs();
    }


    public void trainNetworkWithEpochs(ArrayList<DataValue> dataset, ArrayList<DataValue> testset, int nOfEpochs, GraphPane graphPane) {

        double precision;
        double testset_precision;
        double epoch_squared_error;
        double mean_squared_error;
        int true_positive_count;
        int false_positive_count;

        // Check data set
        if (checkDataset(dataset, this) == false) {
            return;
        }

        // Normalize data set
        normalizeDataset(dataset);

        // Iterate number of epochs times
        for (int epoch = 0; epoch < nOfEpochs; epoch++) {

            System.out.print("Epoch: " + epoch);
            epoch_squared_error = 0;
            true_positive_count = 0;
            false_positive_count = 0;

            // Dataset shuffle for epoch
            Collections.shuffle(dataset);

            // Iterate over dataset examples
            for (DataValue example : dataset) {

                // Train
                trainNetwork(example.inputs, example.desiredOutputs);

                // Get outputs
                ArrayList<Double> outputs = neuralLayers.get(neuralLayers.size() - 1).getOutputs();


                /*
               * Calculate Info
               * */

                // Check prediction
                switch (checkAnswer(outputs, example.desiredOutputs)) {
                    case TRUE_POSITIVE:
                        true_positive_count++;
                        break;

                    case FALSE_POSITIVE:
                        false_positive_count++;
                        break;
                }

                // Add example's squared error to epoch's total error
                for (int k = 0; k < example.desiredOutputs.size(); k++) {
                    epoch_squared_error += Math.pow(example.desiredOutputs.get(k) - outputs.get(k), 2);
                }

            }


            /*
             * After epoch of training
             * */

            // Calculate mean squared error and precision of epoch
            mean_squared_error = epoch_squared_error / dataset.size();
            precision = (double) true_positive_count / (true_positive_count + false_positive_count);
            testset_precision = testing(testset);


            // Add values to line charts
            if (graphPane != null) {
                if (epoch % 5 == 0 || precision > 0.99999) {
                    graphPane.addValueMSE(epoch, mean_squared_error);
                    graphPane.addValuePrecision(epoch, precision);
                    graphPane.addValueTesting(epoch, testset_precision);
                }
            }

            // Print metrics
            DecimalFormat df2 = new DecimalFormat("##.#####");
            System.out.println(", Precision: " + (df2.format(precision))
                             + ", Error: " + (df2.format(mean_squared_error))
                             + ", Test Precision: " + testset_precision);



        }

    }

    public double testing(ArrayList<DataValue> testingSet) {

        double true_positive_count = 0;
        double false_positive_count = 0;
        double precision;

        // Iterate over testing dataset
        for (DataValue datavalue : testingSet) {

            // Evaluate
            ArrayList<Double> outputs = evaluate(datavalue.inputs);

            // Check prediction
            switch (checkAnswer(outputs, datavalue.desiredOutputs)) {
                case TRUE_POSITIVE:
                    true_positive_count++;
                    break;

                case FALSE_POSITIVE:
                    false_positive_count++;
                    break;
            }
        }

        precision = (double) true_positive_count / (true_positive_count + false_positive_count);
        return precision;
    }

}
