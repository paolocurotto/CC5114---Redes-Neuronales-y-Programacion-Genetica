package neural_network;

import java.util.ArrayList;

public class NeuronLayer {

    public ArrayList<SigmoidNeuron> neurons = new ArrayList<>();
    private NeuronLayer previousLayer;
    private NeuronLayer nextLayer;

    NeuronLayer(int n_neurons, int n_weights_per_neuron) {
        for (int n = 0; n < n_neurons; n++) {
            neurons.add(new SigmoidNeuron(n_weights_per_neuron));
        }
    }

    void feedLayer(ArrayList<Double> inputs) {
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
    void backwardPropagate(ArrayList<Double> expectedOutputs) {
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
            SigmoidNeuron cr = this.neurons.get(n);
            double output = cr.getOutput();

            // Calculate error
            double error = 0;
            for (SigmoidNeuron nextNeuron : nextLayer.getNeurons()) {
                error += nextNeuron.getWeights().get(n) * nextNeuron.getDelta();
            }

            // Calculate delta
            double delta = error * output * (1.0 - output);
            cr.setDelta(delta);
        }

        // Continue back-propagation
        if (previousLayer != null) {
            previousLayer.backwardPropagateError();
        }
    }

    void updateWeights(ArrayList<Double> inputs) {
        // Update weights and bias for each neuron in the layer
        for (SigmoidNeuron neuron : this.neurons) {
            // Update each weight
            for (int n = 0; n < neuron.getWeights().size(); n++) {
                neuron.getWeights().set(n, neuron.getWeights().get(n) + (neuron.getLearningRate() * neuron.getDelta() * inputs.get(n)));
            }

            // Update bias
            neuron.setBias(neuron.getBias() + (neuron.getLearningRate() * neuron.getDelta()));
        }

        // Continue weights update on next layer
        if (nextLayer != null) {
            nextLayer.updateWeights(getOutputs());
        }
    }

    ArrayList<Double> getOutputs() {
        ArrayList<Double> outputList = new ArrayList<>();
        for (SigmoidNeuron n : this.neurons) {
            outputList.add(n.getOutput());
        }
        return outputList;
    }

    public ArrayList<SigmoidNeuron> getNeurons() { return this.neurons; }
    void setNextLayer(NeuronLayer neuronLayer) { this.nextLayer = neuronLayer; }
    void setPreviousLayer(NeuronLayer neuronLayer) { this.previousLayer = neuronLayer; }

}
