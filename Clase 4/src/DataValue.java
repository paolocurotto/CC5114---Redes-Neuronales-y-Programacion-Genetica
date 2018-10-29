import java.util.ArrayList;
import java.util.Collections;

/**
 * Set of inputs and desired outputs
 * */

public class DataValue {

    public ArrayList<Double> inputs = new ArrayList<>();
    public ArrayList<Double> desiredOutputs = new ArrayList<>();

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

    public static ArrayList<DataValue> normalizeDataset(ArrayList<DataValue> dataset) {

        ArrayList<DataValue> normalizedDataset = new ArrayList<>();

        /*
            dL = inputs' lowest value
            dH = inputs' highest value
        */

        double dL = Double.MAX_VALUE;
        double dH = Double.MIN_VALUE;
        double nL = 0;
        double nH = 0;

        for (DataValue dataValue : dataset) {
            dL = Math.min(dL, Collections.min(dataValue.inputs));
            dH = Math.max(dH, Collections.max(dataValue.inputs));
        }

        for (DataValue dataValue : dataset) {

            for (double input : dataValue.inputs) {

                //double[] norminp = new double[];

                //normalizedDataset.add(new DataValue())

            }
        }



        return normalizedDataset;
    }

    enum Prediction {
        TRUE_POSITIVE,
        FALSE_POSITIVE,
        TRUE_NEGATIVE,
        FALSE_NEGATIVE,
    }

    public static Prediction checkAnswer(ArrayList<Double> real, ArrayList<Double> desired) {

        double precision = 0.7;
        for (int i = 0; i < desired.size(); i++) {

            // This output must be 1
            if (desired.get(i) == 1) {

                // Check if output got gives 1 in this output
                if (real.get(i) > precision) {
                    return Prediction.TRUE_POSITIVE;
                } else {
                    return Prediction.FALSE_POSITIVE;
                }
            }
        }

        // Noone is positive
        for (int i = 0; i < desired.size(); i++) {

            // Check if any output got gives 1 in this output
            if (real.get(i) > precision) {
                return Prediction.FALSE_NEGATIVE;
            }
        }
        return Prediction.TRUE_NEGATIVE;
    }


}
