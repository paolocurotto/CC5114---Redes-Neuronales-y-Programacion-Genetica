import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class XOR_Test extends Application {

    private int n_rects = 100;
    private int width = 400;
    private int height = 400;
    private Canvas canvas = new Canvas(width, height);
    private ArrayList<XorValues> xor_values = new ArrayList<>();
    private NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {2, 10, 1});
    private Pane root = new Pane();

    public static void main (String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

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





        ArrayList<Double> inputs = new ArrayList<>();

        new AnimationTimer() {

            int a = 0;

            @Override
            public void handle(long now) {

                a++;

                if (a % 100 == 0) {
                    System.out.print("iter = " + a + ", ");
                }

                for (int b = 0; b <= 500; b++) {
                    int r = ThreadLocalRandom.current().nextInt(0, xor_values.size());
                    neuralNetwork.trainNetwork(xor_values.get(r).inputs, xor_values.get(r).results);
                }

                int res = width / n_rects;


                for (int x = 0; x < n_rects; x++) {

                    for (int y = 0; y < n_rects; y++) {

                        inputs.clear();
                        inputs.add(x / (n_rects - 1.0));
                        inputs.add(y / (n_rects - 1.0));
                        double output = neuralNetwork.evaluate(inputs).get(0);
                        int grey_c = (int) (255 * output);
                        gc.setFill(Color.rgb(grey_c, grey_c, grey_c));
                        gc.fillRect(x * res, y * res, res, res);
                    }
                }
            }
        }.start();

    }

    private class XorValues {

        ArrayList<Double> inputs = new ArrayList<>();
        ArrayList<Double> results = new ArrayList<>();
        XorValues(double x1, double x2, double output) {
            inputs.add(x1);
            inputs.add(x2);
            results.add(output);
        }
    }
}
