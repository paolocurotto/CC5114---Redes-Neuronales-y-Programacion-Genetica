package tarea1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import neural_network.DataValue;
import neural_network.NeuralNetwork;
import utils_graphs.GraphPane;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        int nAttributes = 4; // ->
        int nHidden = 100;
        int nClasses = 3; // ->

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {nAttributes, 25, nClasses});

        //ArrayList<DataValue> trainingSet = DatasetParserPokerHands.parseDataset("hands_training.txt");
        //ArrayList<DataValue> pokerTrainingSet = DatasetParserPokerHands.parseDataset("hands_testing.txt");

        //ArrayList<DataValue> trainingSet = DatasetParserForests.parseDataset("training.csv");

        ArrayList<DataValue> trainingSet = DatasetParserIris.parseDataset("iris.txt");


        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Poker hands training");
        stage.setScene(scene);
        stage.show();


        neuralNetwork.trainNetworkWithEpochs(trainingSet, 100000, graph);

    }
}
