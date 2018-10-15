import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        Graph g = new Graph();

        Pane d = g.getGraph();

        Scene scene = new Scene(d, Color.WHITESMOKE);
        stage.setTitle("Neural Network");
        stage.setScene(scene);
        stage.show();


        g.setXrange(10);
        g.setYrange(1);

        g.drawPoint(2, 0.5);
        g.drawPoint(7, 0.1);
        g.drawPoint(10, 0);
    }
}
