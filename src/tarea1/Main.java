package tarea1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import neural_network.DataValue;
import neural_network.NeuralNetwork;
import utils_graphs.GraphPane;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {
                9,  // Number of inputs
                9,  // Hidden layer
                4,  // Output layer
        });

        ArrayList<DataValue> trainingSet = DatasetParserForests.parseDataset("training.csv");
        ArrayList<DataValue> testingSet = DatasetParserForests.parseDataset("testing.csv");

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Neural Network - Forests dataset");
        stage.setScene(scene);
        stage.show();

        Collections.shuffle(trainingSet);
        Collections.shuffle(testingSet);

        neuralNetwork.trainNetworkWithEpochs(trainingSet, testingSet, 8000, graph);
    }
}
