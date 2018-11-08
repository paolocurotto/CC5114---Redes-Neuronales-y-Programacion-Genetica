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

        int nAttributes = 4; //9->forests
        int nClasses = 3; //4->forets

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {nAttributes, 1000, nClasses});
        //ArrayList<DataValue> trainingSet = DatasetParserPokerHands.parseDataset("hands_training.txt");
        //ArrayList<DataValue> pokerTrainingSet = DatasetParserPokerHands.parseDataset("hands_testing.txt");

        ArrayList<DataValue> trainingSet = DatasetParserForests.parseDataset("training.csv");
        ArrayList<DataValue> testingSet = DatasetParserForests.parseDataset("testing.csv");

        ArrayList<DataValue> irisSet = DatasetParserIris.parseDataset("iris.txt");

        ArrayList<DataValue> iris_train = new ArrayList<>();
        ArrayList<DataValue> iris_test = new ArrayList<>();

        Collections.shuffle(irisSet);
        int i = 0;
        for (DataValue irisExample : irisSet) {
            if (i++ % 5 != 0) {
                iris_train.add(irisExample);
            } else {
                iris_test.add(irisExample);
            }
        }

        System.out.println("train size = " + iris_train.size() + ", testsize = " + iris_test.size());


        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Poker hands training");
        stage.setScene(scene);
        stage.show();



        //neuralNetwork.trainNetworkWithEpochs(trainingSet, testingSet, 300, graph);
        neuralNetwork.trainNetworkWithEpochs(iris_train, iris_test, 1000, graph);

        //neuralNetwork.testing(testingSet);

    }
}
