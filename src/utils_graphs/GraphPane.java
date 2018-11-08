package utils_graphs;

import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.chart.LineChart;


public class GraphPane {

    private static int width = 400;
    private static int height = 500;

    private LineChart lineChartMSE;
    private LineChart lineChartPrecision;
    private LineChart lineChartTesting;

    private XYChart.Series seriesMSE = new XYChart.Series();
    private XYChart.Series seriesPrecision = new XYChart.Series();
    private XYChart.Series redLine = new XYChart.Series();
    private XYChart.Series redLine2 = new XYChart.Series();
    private XYChart.Series redLine3 = new XYChart.Series();
    private XYChart.Series seriesTesting = new XYChart.Series();


    public GraphPane() {
        this(width, height);
    }

    GraphPane(int w, int h) {

        // defining axes
        NumberAxis xAxis_epoch_mse = new NumberAxis();
        NumberAxis xAxis_epoch_p = new NumberAxis();
        NumberAxis xAxis_epoch_testing = new NumberAxis();
        NumberAxis yAxis_mse = new NumberAxis();
        NumberAxis yAxis_precision = new NumberAxis();
        NumberAxis yAxis_testing = new NumberAxis();

        xAxis_epoch_mse.setLabel("Epochs");
        xAxis_epoch_p.setLabel("Epochs");
        xAxis_epoch_testing.setLabel("Epochs");
        yAxis_mse.setLabel("Mean Squared Error");
        yAxis_precision.setLabel("Precision");
        yAxis_testing.setLabel("Precision Test dataset");

        // creating charts
        lineChartMSE = new LineChart(xAxis_epoch_mse, yAxis_mse);
        lineChartPrecision = new LineChart(xAxis_epoch_p, yAxis_precision);
        lineChartTesting = new LineChart(xAxis_epoch_testing, yAxis_testing);
        lineChartMSE.setPrefSize(w, h);
        lineChartPrecision.setPrefSize(w, h);
        lineChartTesting.setPrefSize(w, h);
        lineChartMSE.setTitle("Mean squared error");
        lineChartPrecision.setTitle("Precision");
        lineChartTesting.setTitle("Precision Test datset");
        lineChartMSE.getData().addAll(seriesMSE, redLine2);
        lineChartPrecision.getData().addAll(redLine, seriesPrecision);
        lineChartTesting.getData().addAll(redLine3, seriesTesting);

        // set colors
        Node line_mse = seriesMSE.getNode().lookup(".chart-series-line");
        Node line_p = seriesPrecision.getNode().lookup(".chart-series-line");
        Node line_test = seriesTesting.getNode().lookup(".chart-series-line");
        Node red_line = redLine.getNode().lookup(".chart-series-line");
        Node red_line2 = redLine2.getNode().lookup(".chart-series-line");
        Node red_line3 = redLine3.getNode().lookup(".chart-series-line");
        line_mse.setStyle("-fx-stroke: #0000cd;" + "-fx-stroke-width: 2px;");
        line_p.setStyle("-fx-stroke: #228b22;" + "-fx-stroke-width: 2px;");
        line_test.setStyle("-fx-stroke: #228b22;" + "-fx-stroke-width: 2px;");
        red_line.setStyle("-fx-stroke: #ff0000;" + "-fx-stroke-width: 0.4px;");
        red_line2.setStyle("-fx-stroke: #ff0000;" + "-fx-stroke-width: 0.4px;");
        red_line3.setStyle("-fx-stroke: #ff0000;" + "-fx-stroke-width: 0.4px;");

        // remove dots
        lineChartMSE.setCreateSymbols(false);
        lineChartPrecision.setCreateSymbols(false);
        lineChartTesting.setCreateSymbols(false);

    }

    public void addValueMSE(double x, double y) {
        redLine2.getData().add(new XYChart.Data(x, 1));
        seriesMSE.getData().add(new XYChart.Data(x, y));
    }

    public void addValuePrecision(double x, double y) {
        redLine.getData().add(new XYChart.Data(x, 1));
        seriesPrecision.getData().add(new XYChart.Data(x, y));
    }

    public void addValueTesting(double x, double y) {
        redLine3.getData().add(new XYChart.Data(x, 1));
        seriesTesting.getData().add(new XYChart.Data(x, y));
    }


    public Pane getLineChart() {
        return new HBox(lineChartMSE, lineChartPrecision, lineChartTesting);
    }

}
