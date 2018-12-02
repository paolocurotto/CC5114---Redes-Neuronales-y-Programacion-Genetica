package tarea2;

import com.sun.istack.internal.Nullable;
import genetic_algorithm.NQueens;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main_Tarea2 extends Application {

    int N = 8; // Queens
    int pop_size = 30; // Population size
    int k = N; // For tournament selection

    @Override
    public void start(Stage stage) throws Exception {
        Charts chart = new Charts();
        NQueens nQueens = new NQueens(N, pop_size, k, chart);
        nQueens.start();
        Scene scene = new Scene(chart.getLineChart(), Color.WHITESMOKE);
        stage.setTitle("Genetic Algorithm - N-Queens");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

}
