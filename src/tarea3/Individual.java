package tarea3;

import neural_network.NeuralNetwork;

import java.awt.*;

import static tarea3.Globals.*;


public class Individual {
    NeuralNetwork neuralNetwork;
    double fitness;
    int lives = INDIVIDUAL_LIVES;
    Color color;
    int y;
    private double real_y;
    int x1;
    int x2;
    private int y_direction = PADDLE_IDLE;
    double hits = 0;
    double acc_distance = 0;
    int time_idle = 0;

    // Initialize individual with random genes
    Individual() {
        int r = 50;
        color = new Color(r, r, r);
        neuralNetwork = new NeuralNetwork(new int[]{5, 12, 3});
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
        /*
        * [0] = Paddle Y position
        * [1] = Ball X position
        * [2] = Ball Y position
        * [3] = Ball X Velocity
        * [4] = Ball Y Velocity
        */
        double[] info = new double[5];
        // Normalize
        double max_vy = BALL_SPEED * Math.sin(BALL_MAX_ANGLE);
        double min_vy = -max_vy;
        double max_vx = BALL_SPEED * Math.cos(BALL_MIN_ANGLE);
        double min_vx = -max_vx;
        info[0] = (real_y) / (WINDOW_HEIGHT - PADDLE_HEIGHT);
        info[1] = (b.real_x) / (WINDOW_WIDTH);
        info[2] = (b.real_y) / (WINDOW_HEIGHT);
        info[3] = (b.vx - min_vx) / (max_vx - min_vx);
        info[4] = (b.vy - min_vy) / (max_vy - min_vy);
        // Action
        int index = neuralNetwork.evaluate_index(info);
        int action = PLAYER_ACTION.get(index);
        if (action == PADDLE_IDLE) {
            time_idle++;
        }
        y_direction = action;
    }

    boolean checkIfHitBall(Ball ball) {
        int by = ball.y;
        // Individual hit ball
        if (y <= by + BALL_RADIUS && by - BALL_RADIUS <= y + PADDLE_HEIGHT) {
            hits++;
            return true;
        }
        // Missed ball
        else {
            double distanceToBall = Math.max(y - (by + BALL_RADIUS), (by - BALL_RADIUS) - (y + PADDLE_HEIGHT));
            double norm_distance = 1 - (distanceToBall / WINDOW_HEIGHT);
            acc_distance = acc_distance + norm_distance;
            return false;
        }
    }

}
