import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class XOR_Test extends Application {

    int n_rects = 100;
    int width = 400;
    int height = 400;
    //public Rectangle[][] grid = new Rectangle[n_rects][n_rects];
    Canvas canvas = new Canvas(width, height);
    public ArrayList<XorValues> xor_values = new ArrayList<>();
    public NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 5, 1});
    public VBox vbox = new VBox();
    public Pane root = new Pane();


    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {


        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, Color.WHITESMOKE);
        stage.setTitle("XOR test");
        stage.setScene(scene);
        stage.show();

        xor_values.add(new XorValues(0, 0, 0));
        xor_values.add(new XorValues(0, 1, 1));
        xor_values.add(new XorValues(1, 0, 1));
        xor_values.add(new XorValues(1, 1, 0));

        // Train n times
        ArrayList<Double> inputs = new ArrayList<>();
        int n = 100000;

        DecimalFormat df2 = new DecimalFormat(".####");

        AnimationTimer timer = new AnimationTimer() {

            int a = 0;

            @Override
            public void handle(long now) {

                a++;

                if (a % 100 == 0) {
                    System.out.print("iter = " + a + ", ");
                }


                for (int b = 0; b <= 20; b++) {
                    int r = ThreadLocalRandom.current().nextInt(0, 4);
                    neuralNetwork.trainNetwork(xor_values.get(r).inputs, xor_values.get(r).results);
                }

                //System.out.println("---------------");

                // Result
                //System.out.println("(0, 0): " + neuralNetwork.evaluate(xor_values.get(0).inputs) + ", expected: " + xor_values.get(0).results);
                //System.out.println("(0, 1): " + neuralNetwork.evaluate(xor_values.get(1).inputs) + ", expected: " + xor_values.get(1).results);
                //System.out.println("(1, 0): " + neuralNetwork.evaluate(xor_values.get(2).inputs) + ", expected: " + xor_values.get(2).results);
                //System.out.println("(1, 1): " + neuralNetwork.evaluate(xor_values.get(3).inputs) + ", expected: " + xor_values.get(3).results);



                int res = width / n_rects;

                if (a % 2 == 0) {

                    for (int x = 0; x < n_rects; x++) {

                        for (int y = 0; y < n_rects; y++) {

                            double test_x = (double) x / (n_rects - 1);
                            double test_y = (double) y / (n_rects - 1);
                            inputs.clear();
                            inputs.add(test_x);
                            inputs.add(test_y);
                            double output = neuralNetwork.evaluate(inputs);
                            int grey_c = (int) (255 * output);
                            Color c = Color.rgb(grey_c, grey_c, grey_c);
                            gc.setFill(c);
                            gc.fillRect(x * res, y * res, res, res);

                        }
                    }

                    // Result
                    System.out.print("(0, 0): " + neuralNetwork.evaluate(xor_values.get(0).inputs) + ", expected: " + xor_values.get(0).results);
                    System.out.print(", (0, 1): " + neuralNetwork.evaluate(xor_values.get(1).inputs) + ", expected: " + xor_values.get(1).results);
                    System.out.print(", (1, 0): " + neuralNetwork.evaluate(xor_values.get(2).inputs) + ", expected: " + xor_values.get(2).results);
                    System.out.println(", (1, 1): " + neuralNetwork.evaluate(xor_values.get(3).inputs) + ", expected: " + xor_values.get(3).results);


                }
            }
        };

        timer.start();

    }


    private class XorValues {

        public ArrayList<Double> inputs = new ArrayList<>();
        public ArrayList<Double> results = new ArrayList<>();

        public XorValues(int x1, int x2, int output) {
            inputs.add((double) x1);
            inputs.add((double) x2);
            results.add((double) output);
        }

    }
}
