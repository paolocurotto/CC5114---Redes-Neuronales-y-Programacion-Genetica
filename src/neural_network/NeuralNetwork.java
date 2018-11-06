package neural_network;

import utils_graphs.GraphPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import static neural_network.DataValue.*;

public class NeuralNetwork {

    private ArrayList<NeuronLayer> neuralLayers = new ArrayList<>();


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


    public void trainNetworkWithEpochs(ArrayList<DataValue> dataset, int nOfEpochs, GraphPane graphPane) {

        // Normalize data set
        normalizeDataset(dataset);

        // Check sizes
        for (DataValue record : dataset) {
            boolean ans = false;
            for (double output : record.desiredOutputs) {
                if (output == 1) {
                    if (ans == true) {
                        System.err.println("More than one '1' in desired outputs");
                        return;
                    }
                    ans = true;
                }
            }
            if (ans == false) {
                System.err.println("No '1' in desired outputs");
                return;
            }
            if (record.inputs.size() != neuralLayers.get(0).getNeurons().get(0).getWeights().size()) {
                System.err.println("Data set record input size != number weights per neuron first hidden layer");
                return;
            }
            else if (record.desiredOutputs.size() != neuralLayers.get(neuralLayers.size() - 1).getNeurons().size()) {
                System.err.println("Data set record desired outputs size != n output neurons");
                return;
            }
        }

        System.out.print("Shuffling dataset...  ");
        Collections.shuffle(dataset);
        System.out.println("Done.");


        // Iterate number of epochs times
        for (int epoch = 0; epoch < nOfEpochs + 1; epoch++) {

            System.out.print("Epoch: " + epoch);

           // Inputs shuffle for each example in data set
            for (DataValue r : dataset) {
                Collections.shuffle(r.inputs);
            }

            // Dataset shuffle for epoch
            Collections.shuffle(dataset);

            double precision;
            double mean_squared_error = 0;
            int true_positive_count = 0;
            int false_positive_count = 0;

            /*double precision_highcard, precision_onepair;
            int true_positive_count_highcard = 0;
            int false_positive_count_highcard = 0;
            int true_positive_count_onepair = 0;
            int false_positive_count_onepair = 0;
            int realhigh=0, realone=0;*/

            // Iterate through data set
            for (DataValue data : dataset) {

                // Get inputs
                ArrayList<Double> inputs = data.inputs;

                // Get desired outputs
                ArrayList<Double> desiredOutputs = data.desiredOutputs;

                // Train
                if (epoch != 0) {
                    trainNetwork(inputs, desiredOutputs);

                } else {
                    // Evaluation before any training
                    neuralLayers.get(0).feedLayer(data.inputs);
                }

                // Get outputs
                ArrayList<Double> outputs = neuralLayers.get(neuralLayers.size() - 1).getOutputs();


                /*
               * Calculate Info
               * */

                // Check prediction
                switch (checkAnswer(outputs, desiredOutputs)) {
                    /*case TRUE_POSITIVE_HIGH_CARD:
                        true_positive_count_highcard++;
                        realhigh++;
                        break;

                    case FALSE_POSITIVE_HIGH_CARD:
                        false_positive_count_highcard++;
                        realone++;
                        break;

                    case TRUE_POSITIVE_ONE_PAIR:
                        true_positive_count_onepair++;
                        realone++;
                        break;

                    case FALSE_POSITIVE_ONE_PAIR:
                        false_positive_count_onepair++;
                        realhigh++;
                        break;
                        */
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
            precision = (double) true_positive_count / (true_positive_count + false_positive_count);

            /*int tothigh = true_positive_count_highcard + false_positive_count_highcard;
            int totone = true_positive_count_onepair + false_positive_count_onepair;
            if (tothigh == 0) {
                tothigh = 1;
            } else if( totone == 0) {
                totone = 1;
            }
            precision_highcard = (double) true_positive_count_highcard / tothigh;
            precision_onepair = (double) true_positive_count_onepair / totone;


            int tot = tothigh + totone;
            precision = (double) (true_positive_count_highcard + true_positive_count_onepair) / tot;
*/
            // Add values to line charts
            if (graphPane != null) {
                if (epoch % 10 == 0) {
                    graphPane.addValueMSE(epoch, mean_squared_error);
                    //graphPane.addValuehighpair(epoch, precision_highcard, precision_onepair);
                    graphPane.addValuePrecision(epoch, precision);
                }
            }

            DecimalFormat df2 = new DecimalFormat("##.#####");

            /*System.out.println(", Predicted High Card("+realhigh+")= " + tothigh + " (" + true_positive_count_highcard + " /" + false_positive_count_highcard + ")"
                    + ", Predicted One Pair("+realone+")= " + totone + " (" + true_positive_count_onepair + " /" + false_positive_count_onepair + ")"
                    + ", Total= " + tot
                    + "."
                    + " OverallPrecision: " + (df2.format(precision))
                    + ", Error: " + (df2.format(mean_squared_error)));
            */

            System.out.println(", OverallPrecision: " + (df2.format(precision))
                             + ", Error: " + (df2.format(mean_squared_error))
                             + ", FP="+false_positive_count);

            if (precision > 0.99999) {
                return;
            }


        }

    }

}
