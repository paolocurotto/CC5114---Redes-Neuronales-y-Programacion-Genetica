import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.chart.LineChart;


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

    LineChart lineChart;
    XYChart.Series chartSeries2 = new XYChart.Series();
    XYChart.Series chartSeries3 = new XYChart.Series();
    XYChart.Series chartSeries4 = new XYChart.Series();
    XYChart.Series chartSeries5 = new XYChart.Series();


    public GraphPane() {
        this(1100, 700);
    }

    public GraphPane(int w, int h) {

        // defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Epoch");
        yAxis.setLabel("Mean Squared Error");

        // creating the chart
        lineChart = new LineChart(xAxis,yAxis);
        lineChart.setPrefSize(w, h);

        lineChart.setTitle("line chart");

        // defining a series
        chartSeries2.setName("2 hidden n");
        chartSeries3.setName("3 hidden n");
        chartSeries4.setName("4 hidden n");
        chartSeries5.setName("5 hidden n");

    }

    public void addValue(double x, double y, int nchart) {

        switch (nchart) {
            case 2:
                chartSeries2.getData().add(new XYChart.Data(x, y));
                break;
            case 3:
                chartSeries3.getData().add(new XYChart.Data(x, y));
                break;

            case 4:
                chartSeries4.getData().add(new XYChart.Data(x, y));
                break;

            case 5:
                chartSeries5.getData().add(new XYChart.Data(x, y));
                break;


            default:
                System.err.println("error case");

        }


    }

    public LineChart getLineChart() {

        lineChart.getData().addAll(chartSeries2, chartSeries3, chartSeries4, chartSeries5);
        return lineChart;
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
