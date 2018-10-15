import java.util.ArrayList;

public class NeuronLayer {

    public ArrayList<SigmoidNeuron> neurons;
    private NeuronLayer previousLayer;
    private NeuronLayer nextLayer;


    public NeuronLayer(int n_neurons, int n_weights_per_neuron) {
        neurons = new ArrayList<>();
        for (int n = 0; n < n_neurons; n++) {
            neurons.add(new SigmoidNeuron(n_weights_per_neuron));
        }
    }


    public void feedLayer(ArrayList<Double> inputs) {

        // Feed each neuron
        for (SigmoidNeuron neuron : neurons) {
            neuron.feed(inputs);
        }

        // Continue with next layer
        if (nextLayer != null) {
            nextLayer.feedLayer(getOutputs());
        }
    }

    // Backward propagation in output layer
    public void backwardPropagate(ArrayList<Double> expectedOutput) {

        double error;
        double delta;
        double output;

        // Check sizes
        if (neurons.size() != expectedOutput.size())
            System.err.println("n output neurons="+neurons.size() + " != n desiredoutput="+ expectedOutput.size());

        // Set delta for each output neuron
        for (int n = 0; n < neurons.size(); n++) {
            output = neurons.get(n).getOutput();
            error = expectedOutput.get(n) - output;
            delta = error * output * (1 - output);
            neurons.get(n).setDelta(delta);
        }

        // Continue backpropagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }
    }

    // Backward propagation in hidden layers
    public void backwardPropagateError() {

        ArrayList<SigmoidNeuron> nextNeurons = nextLayer.getNeurons();
        SigmoidNeuron currentNeuron;
        double error;
        double delta;
        double output;

        // Calculate delta for each neuron in this layer
        for (int n = 0; n < neurons.size(); n++) {

            currentNeuron = neurons.get(n);
            output = currentNeuron.getOutput();

            // Calculate error
            error = 0;
            for (SigmoidNeuron nextNeuron : nextNeurons) {
                error += nextNeuron.getWeights().get(n) * nextNeuron.getDelta();
            }

            // Calculate delta
            delta = error * output * (1 - output);
            currentNeuron.setDelta(delta);

        }

        // Continue back-propagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }

    }

    public void updateWeights(ArrayList<Double> inputs) {

        double new_weight;
        double new_bias;

        // Update weights and bias for each neuron in the layer
        for (SigmoidNeuron neuron : neurons) {

            // Update each weight
            for (int n = 0; n < inputs.size(); n++) {
                new_weight = neuron.getWeights().get(n) + (neuron.learningRate * neuron.getDelta() * inputs.get(n));
                neuron.setWeight(n, new_weight);
            }

            // Update bias
            new_bias = neuron.getBias() + (neuron.learningRate * neuron.getDelta());
            neuron.setBias(new_bias);
        }

        // Continue weights update on next layer
        if (nextLayer != null) {
            nextLayer.updateWeights(getOutputs());
        }

    }

    private ArrayList<Double> getOutputs() {
        ArrayList<Double> o = new ArrayList<>();
        for (SigmoidNeuron n : neurons)
            o.add(n.getOutput());
        return  o;
    }

    public ArrayList<SigmoidNeuron> getNeurons() { return neurons; }
    public NeuronLayer getNextLayer() { return nextLayer;}
    public void setNextLayer(NeuronLayer neuronLayer) { nextLayer = neuronLayer; }
    public NeuronLayer getPreviousLayer() { return previousLayer; }
    public void setPreviousLayer(NeuronLayer neuronLayer) { previousLayer = neuronLayer; }

}
