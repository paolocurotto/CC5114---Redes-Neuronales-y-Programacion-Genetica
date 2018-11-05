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

        int nAttributes = 5; // -> 5 cards in poker hand
        int nHidden = 100;
        int nClasses = 2; // -> 10 possible poker hand rankings

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {nAttributes, 30, nClasses});

        ArrayList<DataValue> pokerTrainingSet = DatasetParser.parsePokerDataset("hands_training.txt");
        //ArrayList<DataValue> pokerTrainingSet = DatasetParser.parsePoker3Dataset("hands_testing.txt");


        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Poker hands training");
        stage.setScene(scene);
        stage.show();


        neuralNetwork.trainNetworkWithEpochs(pokerTrainingSet, 100000, graph);

    }
}
