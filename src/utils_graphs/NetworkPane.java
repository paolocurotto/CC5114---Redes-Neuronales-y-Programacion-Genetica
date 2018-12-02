package utils_graphs;

import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NetworkPane {

    private HBox root;
    private Pane layers_root;

    public NetworkPane() {

        init();

        layers_root = makeLayersPanes(new int[] {2, 3, 1});
    }

    private void init() {
        Label label1 = new Label("Left");
        label1.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        Label label2 = new Label("Center");
        label2.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        Label label3 = new Label("Center");
        label3.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        Label label4 = new Label("Right");
        label4.setStyle("-fx-background-color: blue; -fx-text-fill: white;");

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        Region region3 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);

        Region region4 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);

        Region region5 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);

        //HBox hBox = new HBox(label1, region1, label2, region2, label3, region3, label4);
        HBox hBox = new HBox(region1, label2, region2, label3, region3);
        root = hBox;
    }


    private Pane makeLayersPanes(int[] layers) {

        HBox layersPane = new HBox();
        Region a = new Region();
        a.setPrefWidth(20);
        layersPane.getChildren().add(a);


        int n_layers = layers.length;

        // Make each layer
        for (int i = 0; i < n_layers; i++) {

            // Vbox
            VBox vlayer = new VBox();
            vlayer.setPrefWidth(20);

            // Top filler
            Region topFiller = new Region();
            VBox.setVgrow(topFiller, Priority.ALWAYS);
            vlayer.getChildren().add(topFiller);

            // Make each neuron
            for (int n = 0; n < layers[i]; n++) {
                // Neuron pane
                Pane neuronPane = neuronPane();

                // Filler
                Region v_filler = new Region();
                VBox.setVgrow(v_filler, Priority.ALWAYS);

                vlayer.getChildren().addAll(neuronPane, v_filler);
            }


            // Add layers to layers
            layersPane.getChildren().add(vlayer);

            // If is last layer dont add filler
            if (i != (n_layers - 1)) {
                // Filler
                Region h_filler = new Region();
                HBox.setHgrow(h_filler, Priority.ALWAYS);
                layersPane.getChildren().add(h_filler);
            }



        }

        return layersPane;
    }


    private Pane neuronPane() {
        Pane pane = new Pane();
        Circle neuron = new Circle();
        neuron.setRadius(10);
        neuron.setFill(Color.CORNFLOWERBLUE);
        pane.getChildren().add(neuron);
        pane.setPrefSize(20, 20);
        return pane;
    }

    private Pane queenPane() {
        Pane pane = new Pane();
        Circle neuron = new Circle();
        neuron.setRadius(10);
        neuron.setFill(Color.CORNFLOWERBLUE);
        pane.getChildren().add(neuron);
        pane.setPrefSize(20, 20);
        return pane;
    }

    public Pane getPane() {
        return root;
    }

    public Pane getLayersPane() {
        return layers_root;
    }
}
