import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GraphPane {

    private int width;
    private int height;
    private Pane pane;
    private int margin = 30;

    private double max_x;
    private double max_y;
    private double old_x;
    private double old_y;

    private Color color;

    public GraphPane() {
        this(900, 600);
    }

    public GraphPane(int w, int h) {
        pane = new Pane();
        width = w - (2 * margin);
        height = h - (2 * margin);
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        old_x = -1;
        old_y = -1;

        // Set axes
        Line x_axis = new Line(0, height, width, height);
        Line y_axis = new Line(0, height, 0, 0);
        pane.getChildren().addAll(x_axis, y_axis);

    }

    public void setXrange(double x) {
        max_x = x;
    }

    public void setYrange(double y) {
        max_y = y;
    }

    public void drawPoint(double x, double y) {

        x = x * ((double) width / max_x);
        y = height - (y * ((double) height / max_y));

        if (old_x == -1 && old_y == -1) {
            old_x = x;
            old_y = y;
        }

        Line l = new Line(old_x, old_y, x, y);
        l.setStrokeWidth(1);
        pane.getChildren().add(l);

        old_x = x;
        old_y = y;

    }

    public Pane getGraph() {
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        vbox.setPrefHeight(height + (2 * margin));
        vbox.setPrefWidth(width + (2 * margin));
        Pane h_filler = new Pane();
        h_filler.setPrefWidth(width + (2 * margin));
        h_filler.setPrefHeight(margin);
        Pane v_filler = new Pane();
        v_filler.setPrefWidth(margin);
        v_filler.setPrefHeight(height);
        hbox.getChildren().addAll(v_filler, pane);
        vbox.getChildren().addAll(h_filler, hbox);
        return vbox;
    }
}
