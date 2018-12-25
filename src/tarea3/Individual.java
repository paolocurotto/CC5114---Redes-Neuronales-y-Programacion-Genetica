package tarea3;

import neural_network.NeuralNetwork;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static tarea3.Globals.*;


public class Individual {
    NeuralNetwork neuralNetwork;
    double fitness;
    int lives = LIVES;
    Color color;
    int y;
    double real_y;
    int x1;
    int x2;
    int y_direction = PADDLE_IDLE;
    int n_sign_mutations = 0;
    int n_amp_mutations = 0;
    int hits = 0;
    int mov_used = 0;

    // Initialize individual with random genes
    Individual() {
        //color = Color.WHITE;
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);
        color = new Color(255, 255, 255);
        neuralNetwork = new NeuralNetwork(new int[]{5, 10, 3});
        x1 = PADDLE_A_INITIAL_X_POS;
        x2 = PADDLE_B_INITIAL_X_POS;
        y = PADDLE_INITIAL_Y_POS;
        real_y = y;
    }

    void movePaddle() {
        real_y = real_y + PADDLE_SPEED * y_direction;
        real_y = Math.max(real_y, 0);
        real_y = Math.min(real_y, WINDOW_HEIGHT - PADDLE_HEIGHT);
        y = (int) real_y;
    }

    void setNextMove(Ball b) {
        double[] info = new double[5];
        // [paddle y, ball x, ball y, ball v_x, ball v_y]
        info[0] = real_y;
        info[1] = b.real_x;
        info[2] = b.real_y;
        info[3] = b.vx;
        info[4] = b.vy;
        double[] aux = new double[5];
        aux[0] = info[0];
        aux[1] = info[1];
        aux[2] = info[2];
        aux[3] = info[3];
        aux[4] = info[4];
        //List<Double> outputs = Arrays.stream(info).boxed().collect(Collectors.toList());
        //double vy = outputs.get(4);
        // Normalize
        double max_vy = BALL_SPEED * Math.sin(1.4);
        double min_vy = -max_vy;
        double max_vx = BALL_SPEED * Math.cos(Math.asin(PADDLE_SPEED / BALL_SPEED) + 0.1);
        double min_vx = -max_vx;
        info[0] = (info[0] - 0) / (WINDOW_HEIGHT - PADDLE_HEIGHT);
        info[1] = (info[1] - 0) / (WINDOW_WIDTH);
        info[2] = (info[2] - 0) / (WINDOW_HEIGHT);
        info[3] = (info[3] - min_vx) / (max_vx - min_vx);
        info[4] = (info[4] - min_vy) / (max_vy - min_vy);

        for (int i = 0; i < info.length; i++) {
            double d = info[i];
            if (d < 0 || d > 1) {
                if (i != -1) {
                    System.out.println("Error at " + i + ", d=" + d + ", b4=" + aux[i] + ", ang=");
                }
            }
        }
        int index = neuralNetwork.evaluate_index(info);
        int action = PLAYER_ACTION.get(index);
        if (action != y_direction) {
            mov_used++;
        }
        y_direction = action;
    }

    boolean checkIfHitBall(Ball ball) {
        int by = ball.y;
        if (y < by + BALL_RADIUS && by - BALL_RADIUS < y + PADDLE_HEIGHT) {
            return true;
        }
        else {
            return false;
        }
    }

    void calculateFitness() {
        fitness = hits - 0.1*mov_used;
    }
}
