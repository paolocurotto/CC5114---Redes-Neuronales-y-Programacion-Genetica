package tarea1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import neural_network.DataExample;
import neural_network.Dataset;
import neural_network.NeuralNetwork;
import utils_graphs.GraphPane;

import java.util.ArrayList;
import java.util.Collections;

public class Main_Tarea extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {
                9,  // Number of inputs
                7,  // Hidden layer
                4,  // Output layer
        });

        Dataset trainingSet = DatasetParserForests.parseDataset("training.csv");
        Dataset testingSet = DatasetParserForests.parseDataset("testing.csv");

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Neural Network - Forests dataset");
        stage.setScene(scene);
        stage.show();

        trainingSet.shuffle();
        testingSet.shuffle();

        neuralNetwork.trainNetworkWithEpochs(trainingSet, testingSet, 1000000, graph);
    }
}
