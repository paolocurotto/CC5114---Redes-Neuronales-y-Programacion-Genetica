package Utils;

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

        NetworkPane p = new NetworkPane();
        //Pane s = p.getPane();

        Pane s = p.getLayersPane();

        s.setPrefSize(500, 400);


        Scene scene = new Scene(s, Color.WHITESMOKE);
        stage.setTitle("Neural Network");
        stage.setScene(scene);
        stage.show();
    }
}
