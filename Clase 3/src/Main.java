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

        /*
        Perceptron p = new Perceptron(new double[]{
                ThreadLocalRandom.current().nextDouble(-0.5, 0.5),
                ThreadLocalRandom.current().nextDouble(-0.5, 0.5)},
                ThreadLocalRandom.current().nextDouble(-0.5, 0.5));

        HashMap<Integer, double[]> values = new HashMap<>();
        HashMap<Integer, Integer> desired = new HashMap<>();
        values.put(0, new double[] {0, 0}); desired.put(0, 0);
        values.put(1, new double[] {0, 1}); desired.put(1, 1);
        values.put(2, new double[] {1, 0}); desired.put(2, 1);
        values.put(3, new double[] {1, 1}); desired.put(3, 0);
        */

        HashMap<Integer, ArrayList<Double>> values_network = new HashMap<>();
        HashMap<Integer, ArrayList<Double>> desired_network = new HashMap<>();

        ArrayList<Double> set00 = new ArrayList<>(); set00.add((double) 0); set00.add((double) 0);
        ArrayList<Double> set01 = new ArrayList<>(); set01.add((double) 0); set01.add((double) 1);
        ArrayList<Double> set10 = new ArrayList<>(); set10.add((double) 1); set10.add((double) 0);
        ArrayList<Double> set11 = new ArrayList<>(); set11.add((double) 1); set11.add((double) 1);
        values_network.put(0, set00);
        values_network.put(1, set01);
        values_network.put(2, set10);
        values_network.put(3, set11);

        ArrayList<Double> des00 = new ArrayList<>(); des00.add((double) 0);
        ArrayList<Double> des01 = new ArrayList<>(); des01.add((double) 1);
        ArrayList<Double> des10 = new ArrayList<>(); des10.add((double) 1);
        ArrayList<Double> des11 = new ArrayList<>(); des11.add((double) 0);
        desired_network.put(0, des00);
        desired_network.put(1, des01);
        desired_network.put(2, des10);
        desired_network.put(3, des11);

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 5, 1});

        // Train n times
        int n = 10000;

        graph.setXrange(n);
        graph.setYrange(1);

        for (int a = 0; a < n; a++) {

            int r = ThreadLocalRandom.current().nextInt(0, 4);

            /*
            p.train(values.get(r), desired.get(r));
            // Test after new value
            int guesses = 0;
            for (int i = 0; i < 4; i++) {
                p.set2Inputs(values.get(i)[0], values.get(i)[1]);

                if (p.calc() == desired.get(i)) {
                    guesses++;
                }
            }
            */

            neuralNetwork.trainNetwork(values_network.get(r), desired_network.get(r));

            // Test after new value
            int guesses = 0;
            for (int i = 0; i < 4; i++) {
                double output = neuralNetwork.evaluate(values_network.get(i));
                double desired = desired_network.get(i).get(0);

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
}
