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

    public DataValue(ArrayList<Double> i, ArrayList<Double> o) {
        inputs = i;
        desiredOutputs = o;
    }

    public DataValue(double[] i, double[] o) {

        for (int iter = 0; iter < i.length; iter++) {
            inputs.add(i[iter]);
        }

        for (int iter = 0; iter < o.length; iter++) {
            desiredOutputs.add(o[iter]);
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
     * Check prediction of the neural network
     * */

    enum Prediction {
        TRUE_POSITIVE,
        FALSE_POSITIVE,
        TRUE_NEGATIVE,
        FALSE_NEGATIVE, TRUE_POSITIVE_HIGH_CARD, FALSE_POSITIVE_HIGH_CARD, TRUE_POSITIVE_ONE_PAIR, FALSE_POSITIVE_ONE_PAIR,
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


            /*
            // high card
            if (desired.get(0) == 1) {
                if (index == 0) {
                    return Prediction.TRUE_POSITIVE_HIGH_CARD;
                } else if (index == 1){
                    return Prediction.FALSE_POSITIVE_ONE_PAIR;
                }

            // 1 pair
            } else if (desired.get(1) == 1) {
                if (index == 1) {
                    return Prediction.TRUE_POSITIVE_ONE_PAIR;
                } else if (index == 0){
                    return Prediction.FALSE_POSITIVE_HIGH_CARD;
                }

            } else {
                System.err.println("Error checking answer");
            }
*/

        }

        System.err.println("Error checking answer");
        return null;

    }

}
