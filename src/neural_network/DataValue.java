package neural_network;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Object that wraps set of inputs and desired outputs for 1 record in a data set.
 *
 * & utility stuff
 * */

public class DataValue {

    public ArrayList<Double> inputs = new ArrayList<>();
    public ArrayList<Double> desiredOutputs = new ArrayList<>();

    public DataValue() {

    }


    public DataValue(double[] i, double[] o) {

        for (double anI : i) {
            inputs.add(anI);
        }

        for (double anO : o) {
            desiredOutputs.add(anO);
        }

    }

    /***
     * Returns a normalized data set
     *
     *   dL = inputs' lowest value
     *   dH = inputs' highest value
     *   nL = normalized lowest value desired
     *   nH = normalized highest value desired
     *
     * */
    public static void normalizeDataset(ArrayList<DataValue> dataset) {

        double dL = Double.MAX_VALUE;
        double dH = Double.MIN_VALUE;
        double nL = 0;
        double nH = 1;

        for (DataValue dataValue : dataset) {
            dL = Math.min(dL, Collections.min(dataValue.inputs));
            dH = Math.max(dH, Collections.max(dataValue.inputs));
        }

        for (DataValue dataValue : dataset) {

            for (int i = 0; i < dataValue.inputs.size(); i++) {

                double x = dataValue.inputs.get(i);
                double fx = ( (double) (x - dL) * (nH - nL) / (dH - dL)) + nL;
                dataValue.inputs.set(i, fx);

            }
        }

    }


    /**
     * Check prediction of the neural network comparing output with desired output
     *
     * */

    enum Prediction {
        TRUE_POSITIVE,
        FALSE_POSITIVE,
        TRUE_NEGATIVE,
        FALSE_NEGATIVE,
    }

    public static Prediction checkAnswer(ArrayList<Double> prediction, ArrayList<Double> desired) {

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
     *  Data set and neural network integrity validation before running training
     *
     *  If returns true is okay
     * */

    public static boolean checkDataset(ArrayList<DataValue> dataset, NeuralNetwork neuralNetwork) {

        for (DataValue record : dataset) {

            // Check outputs
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

            // Check sizes
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
