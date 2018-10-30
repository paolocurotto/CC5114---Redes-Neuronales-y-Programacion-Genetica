import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;


public class GraphPane {

    private int width = 900;
    private int height = 800;

    private LineChart lineChartMSE;
    private XYChart.Series chartMSE = new XYChart.Series();
    private LineChart lineChartPrecision;
    private XYChart.Series chartPrecision = new XYChart.Series();
    private XYChart.Series redLine = new XYChart.Series();

    GraphPane() {
        this(1100, 700);
    }

    GraphPane(int w, int h) {

        // defining axes
        NumberAxis xAxis_epoch = new NumberAxis();
        NumberAxis yAxis_mse = new NumberAxis();
        NumberAxis yAxis_precision = new NumberAxis();
        xAxis_epoch.setLabel("Epoch");
        yAxis_mse.setLabel("Mean Squared Error");
        yAxis_precision.setLabel("Precision");

        // creating charts
        lineChartMSE = new LineChart(xAxis_epoch, yAxis_mse);
        lineChartPrecision = new LineChart(xAxis_epoch, yAxis_precision);
        lineChartMSE.setPrefSize(width, height);
        lineChartPrecision.setPrefSize(width, height);

        lineChartMSE.setTitle("Mean squared error");
        lineChartPrecision.setTitle("Precision");

        lineChartMSE.getData().addAll(chartMSE);
        lineChartPrecision.getData().addAll(redLine, chartPrecision);


        // defining series
        chartMSE.setName("mse");
        chartPrecision.setName("precision");
        redLine.setName("p = 1");

        // set colors
        Node line_mse = chartMSE.getNode().lookup(".chart-series-line");
        Node line_p = chartPrecision.getNode().lookup(".chart-series-line");
        Node red_line = redLine.getNode().lookup(".chart-series-line");
        line_mse.setStyle("-fx-stroke: #0000cd;" + "-fx-stroke-width: 2px;"); // set width of line
        line_p.setStyle("-fx-stroke: #228b22;" + "-fx-stroke-width: 2px;"); // set width of line
        red_line.setStyle("-fx-stroke: #ff0000;" + "-fx-stroke-width: 1px;"); // set width of line


        // remove dots
        lineChartMSE.setCreateSymbols(false);
        lineChartPrecision.setCreateSymbols(false);

    }

    public void addValueMSE(double x, double y) {

        chartMSE.getData().add(new XYChart.Data(x, y));
    }

    public void addValuePrecision(double x, double y) {
        redLine.getData().add(new XYChart.Data(x, 1));
        chartPrecision.getData().add(new XYChart.Data(x, y));
    }

    public Pane getLineChart() {
        return new HBox(lineChartMSE, lineChartPrecision);
    }



}
