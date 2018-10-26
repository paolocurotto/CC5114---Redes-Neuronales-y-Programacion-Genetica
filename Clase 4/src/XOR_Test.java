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


        ArrayList<DataValue> dataset = new ArrayList<>();
        dataset.add(new DataValue(new double[] {0, 0}, new double[] {0}));
        dataset.add(new DataValue(new double[] {0, 1}, new double[] {1}));
        dataset.add(new DataValue(new double[] {1, 0}, new double[] {1}));
        dataset.add(new DataValue(new double[] {1, 1}, new double[] {0}));

        new AnimationTimer() {

            int a = 0;

            @Override
            public void handle(long now) {

                a++;

                if (a % 100 == 0) {
                    System.out.println("iter = " + a + ", ");
                }

                int r = ThreadLocalRandom.current().nextInt(0, xor_values.size());

                //neuralNetwork.trainNetworkWithEpochs(dataset, 1, graph);



                int res = width / n_rects;
                for (int x = 0; x < n_rects; x++) {
                    for (int y = 0; y < n_rects; y++) {
                        //inputs.clear();
                        //inputs.add(x / (n_rects - 1.0));
                        //inputs.add(y / (n_rects - 1.0));
                        //double output = neuralNetwork.evaluate(inputs).get(0);
                        //int grey_c = (int) (255 * output);
                        //gc.setFill(Color.rgb(grey_c, grey_c, grey_c));
                        //gc.fillRect(x * res, y * res, res, res);
                    }
                }


            }
        }.start();

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
