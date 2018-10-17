import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class XOR_Test extends Application {

    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        int width = 500;
        int height = 500;
        int n_rects = 50;
        Pane pane = new Pane();
        Rectangle[][] grid = new Rectangle[n_rects][n_rects];

        VBox vbox = new VBox();
        for (int i = 0; i < n_rects; i++) {

            HBox hbox = new HBox();
            for (int k = 0; k < n_rects; k++) {
                Rectangle r = new Rectangle(10, 10);
                hbox.getChildren().add(r);
                grid[i][k] = r;
            }
            vbox.getChildren().add(hbox);
        }

        Scene scene = new Scene(vbox, Color.WHITESMOKE);
        stage.setTitle("XOR test");
        stage.setScene(scene);
        stage.show();


        ArrayList<xor_values> values = new ArrayList<>();
        values.add(new xor_values(0, 0, 0));
        values.add(new xor_values(0, 1, 1));
        values.add(new xor_values(1, 0, 1));
        values.add(new xor_values(1, 1, 0));

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 2, 1});

        // Initial weights
        neuralNetwork.showWeights1();
        neuralNetwork.showWeights2();

        // Train n times
        int n = 500;

        for (int a = 0; a < n; a++) {

            int r = ThreadLocalRandom.current().nextInt(0, 4);

            neuralNetwork.trainNetwork(values.get(r).inputs, values.get(r).results);

            // Draw grid after new value
            for (int i = 0; i < n_rects; i++) {
                for (int k = 0; k < n_rects; k++) {

                    double test_x = (double) k / n_rects;
                    double test_y = (double) i / n_rects;

                    ArrayList<Double> inputs = new ArrayList<>();
                    inputs.add(test_x); inputs.add(test_y);
                    double output = neuralNetwork.evaluate(inputs);

                    int grey_c = (int) (255 * output);
                    Color c = Color.rgb(grey_c, grey_c, grey_c);
                    grid[i][k].setFill(c);
                }
            }

            if (a % 1000 == 0)
                System.out.println("Iteration: " + a);

        }

        neuralNetwork.showWeights1();
        neuralNetwork.showWeights2();



    }

    private class xor_values {

        public ArrayList<Double> inputs;
        public ArrayList<Double> results;
        public xor_values(int x1, int x2, int output) {
            inputs = new ArrayList<>();
            results = new ArrayList<>();
            inputs.add((double) x1);
            inputs.add((double) x2);
            results.add((double) output);
        }

    }
}
