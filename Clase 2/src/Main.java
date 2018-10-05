import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500, Color.WHITESMOKE);
        stage.setTitle("Neural Network");
        stage.setScene(scene);
        stage.show();

        int realOutput;
        int desiredOutput;

        Perceptron p = new Perceptron(new double[]{1, 1}, 1);

        // Random line parameters y = ax + b
        double a = 2;
        double b = -5;
        Line line = new Line(0,0,1000,(a * 1000 + b));
        root.getChildren().add(line);

        // Train a number of times n;
        int n = 700;
        for (int z = 0; z < n; z++) {

            // Random dot
            double x_random = ThreadLocalRandom.current().nextDouble(-500, 500);
            double y_random = ThreadLocalRandom.current().nextDouble(-500, 500);
            x_random = x_random + 500;
            y_random = y_random + 500;
            Circle dot = new Circle(x_random, y_random, 9);
            dot.setOpacity(0.6);
            root.getChildren().add(dot);

            // Generate output
            p.set2Inputs(x_random, y_random);
            realOutput = p.calc();

            // Calculate desired output
            double y = a * x_random + b;
            if (y_random < y) {
                desiredOutput = 0;
                dot.setFill(Color.RED);
            } else {
                desiredOutput = 1;
                dot.setFill(Color.BLUE);
            }

            // Learning algorithm
            double diff = desiredOutput - realOutput;
            double lr = 0.1;
            int n_inputs = p.inputs.length;

            for (int i = 0; i < n_inputs; i++) {
                p.weights[i] = p.weights[i] + (lr * p.inputs[i] * diff);
            }

            p.threshold = p.threshold + (lr * diff);
        }

    }
}
