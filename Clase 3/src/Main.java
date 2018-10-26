import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {

        GraphPane graph = new GraphPane();
        Scene scene = new Scene(graph.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("XOR");
        stage.setScene(scene);
        stage.show();


        ArrayList<xor_values1> values = new ArrayList<>();
        values.add(new xor_values1(0, 0, 0));
        values.add(new xor_values1(0, 1, 1));
        values.add(new xor_values1(1, 0, 1));
        values.add(new xor_values1(1, 1, 0));

        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 2, 1});

        // Train n times
        int n = 5000;

        graph.setXrange(n);
        graph.setYrange(1);

        for (int a = 0; a < n; a++) {

            int r = ThreadLocalRandom.current().nextInt(0, 4);

            neuralNetwork.trainNetwork(values.get(r).inputs, values.get(r).results);

            // Test after new value
            int guesses = 0;
            for (int i = 0; i < 4; i++) {
                double output = neuralNetwork.evaluate(values.get(i).inputs).get(0);
                double desired = values.get(i).results.get(0);

                if (output < 0.5) {
                    output = 0;
                } else {
                    output = 1;
                }

                if (output == desired) {
                    guesses++;
                }
            }


            double performance = (double) guesses / 4;


            graph.addValue(a, performance);

        }


    }

    private class xor_values1 {

        public ArrayList<Double> inputs = new ArrayList<>();
        public ArrayList<Double> results = new ArrayList<>();
        public xor_values1(int x1, int x2, int output) {
            inputs.add((double) x1);
            inputs.add((double) x2);
            results.add((double) output);
        }

    }
}
