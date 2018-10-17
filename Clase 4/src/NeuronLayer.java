import java.util.ArrayList;

public class NeuronLayer {

    private ArrayList<SigmoidNeuron> neurons = new ArrayList<>();
    private NeuronLayer previousLayer;
    private NeuronLayer nextLayer;


    public NeuronLayer(int n_neurons, int n_weights_per_neuron) {

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

        // Check sizes
        if (neurons.size() != expectedOutput.size())
            System.err.println("n output neurons="+neurons.size() + " != n desiredoutput="+ expectedOutput.size());

        // Set delta for each output neuron
        for (int n = 0; n < neurons.size(); n++) {
            double output = neurons.get(n).getOutput();
            double error = expectedOutput.get(n) - output;
            double delta = error * output * (1 - output);
            neurons.get(n).setDelta(delta);
        }

        // Continue backpropagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }
    }

    // Backward propagation in hidden layers
    private void backwardPropagateError() {

        // Calculate delta for each neuron in this layer
        for (int n = 0; n < neurons.size(); n++) {

            SigmoidNeuron currentNeuron = neurons.get(n);
            double output = currentNeuron.getOutput();

            // Calculate error
            double error = 0;
            for (SigmoidNeuron nextNeuron : nextLayer.getNeurons()) {
                error += nextNeuron.getWeights()[n] * nextNeuron.getDelta();
            }

            // Calculate delta
            double delta = error * output * (1 - output);
            currentNeuron.setDelta(delta);

        }

        // Continue back-propagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }

    }

    public void updateWeights(ArrayList<Double> inputs) {

        // Update weights and bias for each neuron in the layer
        for (SigmoidNeuron neuron : neurons) {

            // Check sizes
            if (neuron.getWeights().length != inputs.size())
                System.err.println("n weights neuron="+neuron.getWeights().length + " != n inputs="+ inputs.size());

            // Update each weight
            for (int n = 0; n < neuron.getWeights().length; n++) {
                neuron.getWeights()[n] = neuron.getWeights()[n] + (neuron.getLearningRate() * neuron.getDelta() * inputs.get(n));
            }

            // Update bias
            neuron.setBias(neuron.getBias() + (neuron.getLearningRate() * neuron.getDelta()));
        }

        // Continue weights update on next layer
        if (nextLayer != null) {
            nextLayer.updateWeights(getOutputs());
        }

    }

    private ArrayList<Double> getOutputs() {
        ArrayList<Double> o = new ArrayList<>();
        for (SigmoidNeuron n : neurons) {
            o.add(n.getOutput());
        }
        return o;
    }

    public ArrayList<SigmoidNeuron> getNeurons() { return neurons; }
    public NeuronLayer getNextLayer() { return nextLayer;}
    public void setNextLayer(NeuronLayer neuronLayer) { nextLayer = neuronLayer; }
    public NeuronLayer getPreviousLayer() { return previousLayer; }
    public void setPreviousLayer(NeuronLayer neuronLayer) { previousLayer = neuronLayer; }

}
