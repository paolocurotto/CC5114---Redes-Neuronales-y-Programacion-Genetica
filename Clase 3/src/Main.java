import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getGraph(), Color.WHITESMOKE);
        stage.setTitle("XOR");
        stage.setScene(scene);
        stage.show();


        ArrayList<xor_values> values = new ArrayList<>();
        values.add(new xor_values(0, 0, 0));
        values.add(new xor_values(0, 1, 1));
        values.add(new xor_values(1, 0, 1));
        values.add(new xor_values(1, 1, 0));

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 2, 1});

        // Train n times
        int n = 1000;

        graph.setXrange(n);
        graph.setYrange(1);

        for (int a = 0; a < n; a++) {

            int r = ThreadLocalRandom.current().nextInt(0, 4);

            neuralNetwork.trainNetwork(values.get(r).inputs, values.get(r).results);

            // Test after new value
            int guesses = 0;
            for (int i = 0; i < 4; i++) {
                double output = neuralNetwork.evaluate(values.get(i).inputs);
                double desired = values.get(i).results.get(0);

                //System.out.println("output/desired = " + output + " / " + desired);

                if (output == desired) {
                    guesses++;
                }
            }

            //System.out.println("Performance = " + guesses + " / 4");

            double performance = (double) guesses / 4;

            System.out.println("Performance = " + performance);

            graph.drawPoint(a, performance);

        }


    }

    private class xor_values {

        public ArrayList<Double> inputs;
        public ArrayList<Double> results;
        public xor_values(int x1, int x2, int output) {
            inputs = new ArrayList<>();
            inputs.add((double) x1);
            inputs.add((double) x2);
            results.add((double) output);
        }

    }
}
