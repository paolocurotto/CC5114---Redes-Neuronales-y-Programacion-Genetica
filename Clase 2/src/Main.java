import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        double h = 500, w = 500;

        HBox layout = new HBox();
        Pane root = new Pane();
        GraphPane graph = new GraphPane((int)w,  (int)h);
        Pane sep = new Pane(); sep.setPrefWidth(20);

        root.setPrefHeight(h);
        root.setPrefWidth(w);

        layout.getChildren().addAll(graph.getGraph(), sep, root);
        Scene scene = new Scene(layout, Color.WHITESMOKE);
        stage.setTitle("Neural Network");
        stage.setScene(scene);
        stage.show();

        int realOutput;
        int desiredOutput;

        ArrayList<double[]> train_set = new ArrayList<>();
        ArrayList<double[]> test_set = new ArrayList<>();


        Perceptron p = new Perceptron(new double[]{
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2),
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2)},
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2));

        // Axes
        root.getChildren().add(new Line(0,h/2, w,h/2));
        root.getChildren().add(new Line(w/2,0, w/2, h));

        // Random line parameters y = ax + b
        double a = -2;
        double b = 0;
        int c = 250;
        Line line = new Line(-1000 + c,((a * 1000) - b) + c,1000 + c,((-a * 1000) - b) + c);
        line.setStrokeWidth(2);
        root.getChildren().add(line);

        // Train perceptron n times;
        int n = 100; double x_random; double y_random;
        for (int z = 0; z < n; z++) {

            // Random point
            x_random = ThreadLocalRandom.current().nextDouble(-50, 50);
            y_random = ThreadLocalRandom.current().nextDouble(-50, 50);
            train_set.add(new double[]{x_random, y_random});

            // Generate output
            p.set2Inputs(x_random, y_random);
            realOutput = p.calc();

            // Calculate desired output
            double y = b + (a * x_random);
            if (y_random < y) {
                desiredOutput = 0;
            } else {
                desiredOutput = 1;
            }

            // Learning algorithm
            int diff = desiredOutput - realOutput;
            double lr = 0.1;

            for (int i = 0; i < p.inputs.length; i++) {
                p.weights[i] += lr * p.inputs[i] * diff;
            }

            p.threshold = p.threshold + (lr * diff);
        }

        // Guess position of m dots
        int m = 500;
        for (int z = 0; z < m; z++) {

            // Random dot
            double x = ThreadLocalRandom.current().nextDouble(-50, 50);
            double y = ThreadLocalRandom.current().nextDouble(-50, 50);
            test_set.add(new double[]{x, y});

            // Generate output
            p.set2Inputs(x, y);
            realOutput = p.calc();

            // Draw dot
            Circle dot = new Circle((x * 5) + w/2, (-y * 5) + h/2, 9);
            dot.setOpacity(0.6);
            root.getChildren().add(dot);
            if (realOutput == 0) {
                dot.setFill(Color.RED);
            } else {
                dot.setFill(Color.BLUE);
            }

        }

        // Graph accuracy/n
        int g_x = 0;
        double error_y = 0;
        int guessed = 0;
        Perceptron p2 = new Perceptron(new double[]{
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2),
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2)},
                ThreadLocalRandom.current().nextDouble(-0.2, 0.2));


        for (double[] point : train_set) {

            // Train with new point
            double y = b + (a * point[0]);
            if (point[1] < y) {
                desiredOutput = 0;
            } else {
                desiredOutput = 1;
            }

            p2.train(new double[]{point[0], point[1]}, desiredOutput);

            // Test after it was trained with new point
            guessed = 0;
            for (double[] test_point : test_set) {
                p2.set2Inputs(test_point[0], test_point[1]);
                realOutput = p2.calc();
                double des_y = b + (a * test_point[0]);
                if (test_point[1] < des_y) {
                    desiredOutput = 0;
                } else {
                    desiredOutput = 1;
                }

                if (realOutput == desiredOutput) {
                    guessed++;
                }
            }

            // Graph
            graph.setXrange(train_set.size());
            graph.setYrange(1);

            error_y = (double) guessed / test_set.size();

            graph.drawPoint(g_x, error_y);

            g_x++;
        }

    }
}
