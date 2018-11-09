package neural_network;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Object that wraps set of inputs and desired outputs for 1 record in a data set.
 *
 */

public class DataExample {

    public ArrayList<Double> inputs = new ArrayList<>();
    public ArrayList<Double> desiredOutputs = new ArrayList<>();

    public DataExample() {

    }

    public DataExample(double[] ins, double[] outs) {
        for (double i : ins) {
            inputs.add(i);
        }

        for (double o : outs) {
            desiredOutputs.add(o);
        }
    }

}
