package neural_network;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Object representing dataset of a neural network
 *
 */
public class Dataset {

    public ArrayList<DataExample> dataset = new ArrayList<>();

    public Dataset() {

    }

    public void shuffle() {
        Collections.shuffle(dataset);
    }


    /**
     * Modifies the dataset to the normalized version
     *
     *   dL = inputs' lowest value
     *   dH = inputs' highest value
     *   nL = normalized lowest value desired
     *   nH = normalized highest value desired
     *
     */
    public void normalize() {
        double dL = Double.MAX_VALUE;
        double dH = Double.MIN_VALUE;
        double nL = 0;
        double nH = 1;

        for (DataExample dataExample : dataset) {
            dL = Math.min(dL, Collections.min(dataExample.inputs));
            dH = Math.max(dH, Collections.max(dataExample.inputs));
        }

        for (DataExample dataExample : dataset) {
            for (int i = 0; i < dataExample.inputs.size(); i++) {
                double fx = (dataExample.inputs.get(i) - dL) * (nH - nL) / (dH - dL) + nL;
                dataExample.inputs.set(i, fx);
            }
        }
    }


    /**
     * Checks prediction of the neural network comparing output with desired output
     *
     * */

    enum Prediction {
        TRUE_POSITIVE,
        FALSE_POSITIVE,
        TRUE_NEGATIVE,
        FALSE_NEGATIVE,
    }

    public Prediction checkAnswer(ArrayList<Double> prediction, ArrayList<Double> desired) {

        // Model with 1 output
        if (prediction.size() == 1) {

            // Predicted true
            if (prediction.get(0) > 0.5) {
                if (desired.get(0) == 1) {
                    return Prediction.TRUE_POSITIVE;
                } else if (desired.get(0) == 0) {
                    return Prediction.FALSE_POSITIVE;
                }
            }

            // Predicted false
            else {
                if (desired.get(0) == 1) {
                    return Prediction.FALSE_NEGATIVE;
                } else if (desired.get(0) == 0) {
                    return Prediction.TRUE_NEGATIVE;
                }
            }
        }

        // Model with multiple outputs
        else if (prediction.size() > 1) {

            // Find index of highest value in prediction (= answer)
            int index = 0;
            for (int i = 1; i < desired.size(); i++) {
                if (prediction.get(index) < prediction.get(i)) {
                    index = i;
                }
            }

            // Check if prediction was correct
            if (desired.get(index) == 1) {
                return Prediction.TRUE_POSITIVE;

            } else if (desired.get(index) == 0) {
                return Prediction.FALSE_POSITIVE;
            }
        }

        System.err.println("Error checking answer");
        return null;
    }


    /**
     *  Dataset and neural network integrity validation before running training
     *
     *  Returning true if okay
     */
    public boolean checkDataset(NeuralNetwork neuralNetwork) {

        for (DataExample record : dataset) {
            // Checks outputs
            boolean check_1 = false;
            for (double output : record.desiredOutputs) {
                if (output == 1) {
                    if (check_1) {
                        System.err.println("More than one '1' in desired outputs");
                        return false;
                    }
                    check_1 = true;
                }
            }
            if (!check_1) {
                System.err.println("No '1' in desired outputs");
                return false;
            }

            // Checks sizes
            if (record.inputs.size() != neuralNetwork.neuralLayers.get(0).getNeurons().get(0).getWeights().size()) {
                System.err.println("Data set record input size != number weights per neuron first hidden layer");
                return false;
            }
            else if (record.desiredOutputs.size() != neuralNetwork.neuralLayers.get(neuralNetwork.neuralLayers.size() - 1).getNeurons().size()) {
                System.err.println("Data set record desired outputs size != n output neurons");
                return false;
            }
        }
        return true;
    }

}
