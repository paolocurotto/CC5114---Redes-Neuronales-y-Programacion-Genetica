package genetic_algorithm;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BitsSequence extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        int N = 1; // Size of population
        int n_bits = 100; // Number of bits
        int[] secretSeq = new int[] {0, 0, 1, 0, 0}; // Secret bits sequence

        List<Individual> population = new ArrayList<>();

        // Initialize population
        for (int i = 0; i < N; i++) {
            population.add(new Individual(n_bits));
        }




    }
}

class Individual {
    List<Integer> genes = new ArrayList<>();

    Individual(int nbits) {
        for (int i = 0; i < nbits; i++) {
            genes.add(ThreadLocalRandom.current().nextInt(0, 2));
        }
    }

    public int calculateFitness(int[] target) {
        int value = 0;
        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i) == target[i]) {
                value++;
            }
        }
        return value;
    }
}