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
    public void backwardPropagate(ArrayList<Double> expectedOutputs) {

        // Check sizes
        if (neurons.size() != expectedOutputs.size())
            System.err.println("n output neurons="+neurons.size() + " != n desiredoutput="+ expectedOutputs.size());

        // Set delta for each output neuron
        for (int n = 0; n < neurons.size(); n++) {
            double output = this.neurons.get(n).getOutput();
            double error = expectedOutputs.get(n) - output;
            double delta = error * (output * (1.0 - output));
            this.neurons.get(n).setDelta(delta);
        }

        // Continue backpropagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }
    }

    // Backward propagation in hidden layers
    private void backwardPropagateError() {

        // Calculate delta for each neuron in this layer
        for (int n = 0; n < this.neurons.size(); n++) {

            double output = this.neurons.get(n).getOutput();

            // Calculate error
            double error = 0;
            for (SigmoidNeuron nextNeuron : nextLayer.getNeurons()) {
                error += nextNeuron.getWeights()[n] * nextNeuron.getDelta();
            }

            // Calculate delta
            double delta = error * (output * (1.0 - output));
            this.neurons.get(n).setDelta(delta);

        }

        // Continue back-propagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }

    }

    public void updateWeights(ArrayList<Double> inputs) {

        // Update weights and bias for each neuron in the layer
        for (SigmoidNeuron neuron : this.neurons) {

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
        for (SigmoidNeuron n : this.neurons) {
            o.add(n.getOutput());
        }
        return o;
    }

    public ArrayList<SigmoidNeuron> getNeurons() { return this.neurons; }
    public NeuronLayer getNextLayer() { return this.nextLayer;}
    public void setNextLayer(NeuronLayer neuronLayer) { this.nextLayer = neuronLayer; }
    public NeuronLayer getPreviousLayer() { return this.previousLayer; }
    public void setPreviousLayer(NeuronLayer neuronLayer) { this.previousLayer = neuronLayer; }

}
