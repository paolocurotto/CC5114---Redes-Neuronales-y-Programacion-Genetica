import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main_4 extends Application {

    private int width = 400;
    private int height = 400;

    private NeuralNetwork neuralNetwork2 = new NeuralNetwork(new int[] {2, 2, 1});
    private NeuralNetwork neuralNetwork3 = new NeuralNetwork(new int[] {2, 3, 1});
    private NeuralNetwork neuralNetwork4 = new NeuralNetwork(new int[] {2, 4, 1});
    private NeuralNetwork neuralNetwork5 = new NeuralNetwork(new int[] {2, 5, 1});

    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("XOR linechart");
        stage.setScene(scene);
        stage.show();

        neuralNetwork2.identifier = 2;
        neuralNetwork3.identifier = 3;
        neuralNetwork4.identifier = 4;
        neuralNetwork5.identifier = 5;


        ArrayList<DataValue> dataset = new ArrayList<>();
        dataset.add(new DataValue(new double[] {0, 0}, new double[] {0}));
        dataset.add(new DataValue(new double[] {0, 1}, new double[] {1}));
        dataset.add(new DataValue(new double[] {1, 0}, new double[] {1}));
        dataset.add(new DataValue(new double[] {1, 1}, new double[] {0}));

        neuralNetwork2.trainNetworkWithEpochs(dataset, 2000, graph);
        neuralNetwork3.trainNetworkWithEpochs(dataset, 2000, graph);
        neuralNetwork4.trainNetworkWithEpochs(dataset, 2000, graph);
        neuralNetwork5.trainNetworkWithEpochs(dataset, 2000, graph);


/*
        new AnimationTimer() {
            int a = 0;
            int x = 0, y = 0;
            @Override
            public void handle(long now) {
                a++;
                if (a < 500) {
                    System.out.println("Epoch = " + neuralNetwork.epoch );
                    //graph.addValue(x++, y++);
                    neuralNetwork.trainNetworkWithEpochs(dataset, 1, graph);
                }
            }
        }.start();
*/
    }
}
