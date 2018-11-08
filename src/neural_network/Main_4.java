package neural_network;

import utils_graphs.GraphPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main_4 extends Application {


    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 3, 2});

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("XOR linechart");
        stage.setScene(scene);
        stage.show();

        ArrayList<DataValue> dataset = new ArrayList<>();
        dataset.add(new DataValue(new double[] {0, 0}, new double[] {1, 0}));
        dataset.add(new DataValue(new double[] {0, 50}, new double[] {0, 1}));
        dataset.add(new DataValue(new double[] {50, 0}, new double[] {0, 1}));
        dataset.add(new DataValue(new double[] {50, 50}, new double[] {1, 0}));

        neuralNetwork.trainNetworkWithEpochs(dataset, null, 2000, graph);

    }
}
