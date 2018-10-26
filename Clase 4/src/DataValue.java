import java.util.ArrayList;

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

}
