package tarea3;

import neural_network.NeuralNetwork;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static tarea3.Globals.*;


public class Individual {
    NeuralNetwork neuralNetwork;
    Paddles paddles;
    int fitness;

    // Initialize individual with random genes
    Individual() {
        neuralNetwork = new NeuralNetwork(new int[]{5, 10, 3});
    }

    void movePaddle(Ball b) {
        double[] info = new double[5];
        // [paddle y, ball x, ball y, ball v_x, ball v_y]
        info[0] = paddles.real_y;
        info[1] = b.real_x;
        info[2] = b.real_y;
        info[3] = b.vx;
        info[4] = b.vy;
        List<Double> outputs = Arrays.stream(info).boxed().collect(Collectors.toList());
        double vy = outputs.get(4);
        // Normalize
        double max_vy = BALL_SPEED * Math.sin(1.0471);
        double min_vy = -max_vy;
        double max_vx = BALL_SPEED*Math.cos(0.350);
        double min_vx = -max_vx;
        outputs.set(0, (outputs.get(0) - PADDLE_HEIGHT/2.0) / ((WINDOW_HEIGHT - PADDLE_HEIGHT/2.0) - PADDLE_HEIGHT/2.0));
        outputs.set(1, (outputs.get(1) - 0) / (WINDOW_WIDTH));
        outputs.set(2, (outputs.get(2) - 0) / (WINDOW_HEIGHT));
        outputs.set(3, (outputs.get(3) - min_vx) / (max_vx - min_vx));
        outputs.set(4, (outputs.get(4) - min_vy) / (max_vy - min_vy));

        for (int i = 0; i < outputs.size(); i++) {
            double d = outputs.get(i);
            if (d < 0 || d > 1) {
                if (i != 1) {
                    System.out.println("Error at " + i + ", d=" + d + ", vy = " + vy);
                }
            }
        }

        int index = neuralNetwork.evaluate_index(outputs);
        int action = PLAYER_ACTION.get(index);
    }


}
