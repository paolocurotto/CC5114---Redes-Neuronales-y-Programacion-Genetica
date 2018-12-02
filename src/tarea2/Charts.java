package tarea2;

import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.chart.LineChart;

/**
 * Class to create line charts in a javafx pane
 */
public class Charts {

    private static int width = 700;
    private static int height = 500;
    private LineChart lineChartFitness;
    private XYChart.Series seriesFitnessBest = new XYChart.Series();
    private XYChart.Series seriesFitnessAVG = new XYChart.Series();
    private XYChart.Series seriesFitnessWorst = new XYChart.Series();
    private XYChart.Series redLine = new XYChart.Series();



    public Charts() {
        this(width, height);
    }

    Charts(int w, int h) {
        // Defining axes
        NumberAxis xAxis_generation = new NumberAxis();
        NumberAxis yAxis_fitness = new NumberAxis();
        yAxis_fitness.setMinorTickVisible(false);
        xAxis_generation.setLabel("Generation");
        yAxis_fitness.setLabel("Fitness");

        // Create charts
        lineChartFitness = new LineChart(xAxis_generation, yAxis_fitness);
        lineChartFitness.setPrefSize(w, h);
        lineChartFitness.setTitle("Genetic algorithm");
        lineChartFitness.getData().addAll(seriesFitnessBest, seriesFitnessAVG, seriesFitnessWorst, redLine);

        // Set colors
        Node line_fitness = seriesFitnessBest.getNode().lookup(".chart-series-line");
        Node line_fitness_avg = seriesFitnessAVG.getNode().lookup(".chart-series-line");
        Node line_fitness_w = seriesFitnessWorst.getNode().lookup(".chart-series-line");
        Node red_line = redLine.getNode().lookup(".chart-series-line");
        line_fitness.setStyle("-fx-stroke: #006600;" + "-fx-stroke-width: 1.7px;");
        line_fitness_avg.setStyle("-fx-stroke: #ff9933;" + "-fx-stroke-width: 1.5px;");
        line_fitness_w.setStyle("-fx-stroke: #ff0000;" + "-fx-stroke-width: 1.5px;");
        red_line.setStyle("-fx-stroke: #000000;" + "-fx-stroke-width: 0.4px;");

        // Remove dots
        lineChartFitness.setCreateSymbols(false);
    }

    public void addValueBestFitness(double g, double f, int target) {
        redLine.getData().add(new XYChart.Data(g, target));
        seriesFitnessBest.getData().add(new XYChart.Data(g, f));
    }

    public void addValueAverageFitness(double g, double f, int target) {
        seriesFitnessAVG.getData().add(new XYChart.Data(g, f));
    }

    public void addValueWorstFitness(double g, double f, int target) {
        seriesFitnessWorst.getData().add(new XYChart.Data(g, f));
    }

    // Returns javafx pane of the charts
    public Pane getLineChart() {
        return new HBox(lineChartFitness);
    }
}
