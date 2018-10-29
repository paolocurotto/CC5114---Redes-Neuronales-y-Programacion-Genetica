import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.chart.LineChart;


public class GraphPane {

    private int width = 500;
    private int height = 400;
    private Pane pane;
    private int margin = 30;

    private Color color;

    LineChart lineChartMSE;
    XYChart.Series chartMSE = new XYChart.Series();
    LineChart lineChartPrecision;
    XYChart.Series chartPrecision = new XYChart.Series();

    public GraphPane() {
        this(1100, 700);
    }

    public GraphPane(int w, int h) {

        // defining the axes
        final NumberAxis xAxis_epoch = new NumberAxis();
        final NumberAxis yAxis_mse = new NumberAxis();
        xAxis_epoch.setLabel("Epoch");
        yAxis_mse.setLabel("Mean Squared Error");

        final NumberAxis yAxis_precision = new NumberAxis();
        yAxis_precision.setLabel("Precision");


        // creating charts
        lineChartMSE = new LineChart(xAxis_epoch,yAxis_mse);
        lineChartPrecision = new LineChart(xAxis_epoch,yAxis_precision);
        lineChartMSE.setPrefSize(width, height);
        lineChartPrecision.setPrefSize(width, height);

        lineChartMSE.setTitle("Mean squared error");
        lineChartPrecision.setTitle("Precision");

        // defining a series
        chartMSE.setName("mse");
        chartPrecision.setName("p");

    }

    public void addValueMSE(double x, double y) {

        chartMSE.getData().add(new XYChart.Data(x, y));
    }

    public void addValuePrecision(double x, double y) {

        chartPrecision.getData().add(new XYChart.Data(x, y));
    }

    public Pane getLineChart() {

        lineChartMSE.getData().addAll(chartMSE);
        lineChartPrecision.getData().addAll(chartPrecision);

        HBox hBox = new HBox();

        hBox.getChildren().addAll(lineChartMSE, lineChartPrecision);

        return hBox;
    }



}
