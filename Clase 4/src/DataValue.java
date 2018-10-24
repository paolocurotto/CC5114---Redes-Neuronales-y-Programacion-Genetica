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

}
