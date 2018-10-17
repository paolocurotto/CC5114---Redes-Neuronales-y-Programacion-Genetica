import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import sun.nio.ch.Net;

import java.applet.Applet;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class XOR_Test extends Application {

    int n_rects = 40;
    public Rectangle[][] grid = new Rectangle[n_rects][n_rects];
    public ArrayList<xor_values> values = new ArrayList<>();
    public NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 2, 1});
    public VBox vbox = new VBox();


    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        for (int i = 0; i < n_rects; i++) {

            HBox hbox = new HBox();
            for (int k = 0; k < n_rects; k++) {

                hbox.getChildren().add(new Rectangle(7, 7));
                //grid[i][k] = r;
            }
            vbox.getChildren().add(hbox);
        }

        Scene scene = new Scene(vbox, Color.WHITESMOKE);
        stage.setTitle("XOR test");
        stage.setScene(scene);
        stage.show();

        values.add(new xor_values(0, 0, 0));
        values.add(new xor_values(0, 1, 1));
        values.add(new xor_values(1, 0, 1));
        values.add(new xor_values(1, 1, 0));


        // Initial weights
        neuralNetwork.showWeights1();
        neuralNetwork.showWeights2();

        Network_training nt = new Network_training();
        //nt.start();

        // Train n times
        ArrayList<Double> inputs = new ArrayList<>();

        int n = 100000;
        if (true) {
            for (int a = 0; a < n; a++) {

                int r = ThreadLocalRandom.current().nextInt(0, 4);

                neuralNetwork.trainNetwork(values.get(r).inputs, values.get(r).results);

                if (a % 10000 == 0) {
                    int x = 0, y = 0;
                    for (Node hb : vbox.getChildren()) {
                        y = 0;
                        for (Node rect : ((HBox) hb).getChildren()) {
                            double test_x = (double) x / (n_rects - 1);
                            double test_y = (double) y / (n_rects - 1);
                            inputs.clear();
                            inputs.add(test_x);
                            inputs.add(test_y);
                            double output = neuralNetwork.evaluate(inputs);
                            int grey_c = (int) (255 * output);
                            //System.out.println("output = " + output);
                            Color c = Color.rgb(grey_c, grey_c, grey_c);
                            ((Rectangle) rect).setFill(c);
                            y++;
                        }
                        x++;
                    }
                }
                if (a % 10000000 == 0)
                    System.out.println("Iteration: " + a);
            }
        }
        neuralNetwork.showWeights1();
        neuralNetwork.showWeights2();
    }

    private class Network_training extends Thread {


        @Override
        public void run() {
            int n = 1000000;

            for (int a = 0; a < n; a++) {

                int r = ThreadLocalRandom.current().nextInt(0, 4);

                neuralNetwork.trainNetwork(values.get(r).inputs, values.get(r).results);

                // Draw grid after new value
                int x = 0, y = 0;
                for (Node hb : vbox.getChildren()) {

                    y = 0;
                    for (Node rect : ((HBox) hb).getChildren()) {
                        double test_x = (double) x / (n_rects - 1);
                        double test_y = (double) y / (n_rects - 1);


                        ArrayList<Double> inputs = new ArrayList<>();
                        inputs.add(test_x); inputs.add(test_y);
                        double output = neuralNetwork.evaluate(inputs);

                        int grey_c = (int) (255 * output);
                        //System.out.println("output = " + output);
                        Color c = Color.rgb(grey_c, grey_c, grey_c);
                        ((Rectangle) rect).setFill(c);
                        y++;
                    }

                    x++;
                }

                //if (a % 1000 == 0)
                    //System.out.println("Iteration: " + a);

            }
        }
    }

    private class xor_values {

        public ArrayList<Double> inputs = new ArrayList<>();
        public ArrayList<Double> results = new ArrayList<>();
        public xor_values(int x1, int x2, int output) {
            inputs.add((double) x1);
            inputs.add((double) x2);
            results.add((double) output);
        }

    }
}
