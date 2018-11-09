package tarea1;

import neural_network.NeuralNetwork;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class Tests {

    private NeuralNetwork neuralNetwork_1 = new NeuralNetwork(new int[] {2, 1, 1});
    private NeuralNetwork neuralNetwork_2 = new NeuralNetwork(new int[] {2, 2, 2});

    /**
     * Test case 1
     *
     */
    @Before
    public void setTestcase_1() {
        // Set Neuron 1
        neuralNetwork_1.neuralLayers.get(0).neurons.get(0).setBias(0.5);
        neuralNetwork_1.neuralLayers.get(0).neurons.get(0).setLearningRate(0.5);
        ArrayList<Double> weights_1 = new ArrayList<>();
        weights_1.add(0.4);
        weights_1.add(0.3);
        neuralNetwork_1.neuralLayers.get(0).neurons.get(0).setWeights(weights_1);

        // Set Neuron 2
        neuralNetwork_1.neuralLayers.get(1).neurons.get(0).setBias(0.4);
        neuralNetwork_1.neuralLayers.get(1).neurons.get(0).setLearningRate(0.5);
        ArrayList<Double> weights_2 = new ArrayList<>();
        weights_2.add(0.3);
        neuralNetwork_1.neuralLayers.get(1).neurons.get(0).setWeights(weights_2);
    }

    @Test
    public void neuralNetworkTest_1() {
        ArrayList<Double> input_1 = new ArrayList<>();
        input_1.add((double) 1);
        input_1.add((double) 1);
        ArrayList<Double> desired_1 = new ArrayList<>();
        desired_1.add((double) 1);
        neuralNetwork_1.trainNetwork(input_1, desired_1);

        // Test neuron 1
        assertEquals(0.502101508999489, neuralNetwork_1.neuralLayers.get(0).neurons.get(0).getBias()); // Test bias
        assertEquals(0.40210150899948904, neuralNetwork_1.neuralLayers.get(0).neurons.get(0).getWeights().get(0)); // Test weight 1
        assertEquals(0.302101508999489, neuralNetwork_1.neuralLayers.get(0).neurons.get(0).getWeights().get(1)); // Test weight 2

        // Test neuron 2
        assertEquals(0.43937745312797394, neuralNetwork_1.neuralLayers.get(1).neurons.get(0).getBias()); // Test bias
        assertEquals(0.33026254863991883, neuralNetwork_1.neuralLayers.get(1).neurons.get(0).getWeights().get(0)); // Test weight
    }


    /**
     * Test case 2
     *
     */
    @Before
    public void setTestcase_2() {
        // Set Neuron 1
        neuralNetwork_2.neuralLayers.get(0).neurons.get(0).setBias(0.5);
        neuralNetwork_2.neuralLayers.get(0).neurons.get(0).setLearningRate(0.5);
        ArrayList<Double> weights_1 = new ArrayList<>();
        weights_1.add(0.7);
        weights_1.add(0.3);
        neuralNetwork_2.neuralLayers.get(0).neurons.get(0).setWeights(weights_1);

        // Set Neuron 2
        neuralNetwork_2.neuralLayers.get(0).neurons.get(1).setBias(0.4);
        neuralNetwork_2.neuralLayers.get(0).neurons.get(1).setLearningRate(0.5);
        ArrayList<Double> weights_2 = new ArrayList<>();
        weights_2.add(0.3);
        weights_2.add(0.7);
        neuralNetwork_2.neuralLayers.get(0).neurons.get(1).setWeights(weights_2);

        // Set Neuron 3
        neuralNetwork_2.neuralLayers.get(1).neurons.get(0).setBias(0.3);
        neuralNetwork_2.neuralLayers.get(1).neurons.get(0).setLearningRate(0.5);
        ArrayList<Double> weights_3 = new ArrayList<>();
        weights_3.add(0.2);
        weights_3.add(0.3);
        neuralNetwork_2.neuralLayers.get(1).neurons.get(0).setWeights(weights_3);

        // Set Neuron 4
        neuralNetwork_2.neuralLayers.get(1).neurons.get(1).setBias(0.6);
        neuralNetwork_2.neuralLayers.get(1).neurons.get(1).setLearningRate(0.5);
        ArrayList<Double> weights_4 = new ArrayList<>();
        weights_4.add(0.4);
        weights_4.add(0.2);
        neuralNetwork_2.neuralLayers.get(1).neurons.get(1).setWeights(weights_4);
    }

    @Test
    public void neuralNetworkTest_2() {
        ArrayList<Double> input_2 = new ArrayList<>();
        input_2.add((double) 1);
        input_2.add((double) 1);
        ArrayList<Double> desired_2 = new ArrayList<>();
        desired_2.add((double) 1);
        desired_2.add((double) 1);
        neuralNetwork_2.trainNetwork(input_2, desired_2);

        // Test neuron 1
        assertEquals(0.5025104485493278, neuralNetwork_2.neuralLayers.get(0).neurons.get(0).getBias()); // Test bias
        assertEquals(0.7025104485493278, neuralNetwork_2.neuralLayers.get(0).neurons.get(0).getWeights().get(0)); // Test weight 1
        assertEquals(0.3025104485493278, neuralNetwork_2.neuralLayers.get(0).neurons.get(0).getWeights().get(1)); // Test weight 2

        // Test neuron 2
        assertEquals(0.40249801135748337, neuralNetwork_2.neuralLayers.get(0).neurons.get(1).getBias()); // Test bias
        assertEquals(0.30249801135748333, neuralNetwork_2.neuralLayers.get(0).neurons.get(1).getWeights().get(0)); // Test weight 1
        assertEquals(0.7024980113574834, neuralNetwork_2.neuralLayers.get(0).neurons.get(1).getWeights().get(1)); // Test weight 2

        // Test neuron 3
        assertEquals(0.3366295422515899, neuralNetwork_2.neuralLayers.get(1).neurons.get(0).getBias()); // Test bias
        assertEquals(0.22994737881955657, neuralNetwork_2.neuralLayers.get(1).neurons.get(0).getWeights().get(0)); // Test weight 1
        assertEquals(0.32938362863950127, neuralNetwork_2.neuralLayers.get(1).neurons.get(0).getWeights().get(1)); // Test weight 2

        // Test neuron 4
        assertEquals(0.6237654881509048, neuralNetwork_2.neuralLayers.get(1).neurons.get(1).getBias()); // Test bias
        assertEquals(0.41943005652646226, neuralNetwork_2.neuralLayers.get(1).neurons.get(1).getWeights().get(0)); // Test weight 1
        assertEquals(0.21906429169838573, neuralNetwork_2.neuralLayers.get(1).neurons.get(1).getWeights().get(1)); // Test weight 2
    }
}
