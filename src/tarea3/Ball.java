package tarea3;

import java.awt.*;

import static tarea3.Globals.*;

public class Ball {

    Color color = Color.WHITE;
    int x;
    int y;
    double real_x;
    double real_y;
    double angle_p = 2.6;
    double vx = BALL_SPEED * Math.cos(angle_p);
    double vy = BALL_SPEED * Math.sin(angle_p);
    boolean active = true;

    Ball() {
        x = BALL_INITIAL_X_POS;
        y = BALL_INITIAL_Y_POS;
        real_x = x;
        real_y = y;
    }

    void move() {
        real_x = real_x + vx;
        real_y = real_y + vy;
        x = (int) real_x;
        y = (int) real_y;
    }

    void checkCollisionTopEdge() {
        if (real_y < BALL_TOP_EDGE_HIT_THRESHOLD) {
            real_y = BALL_TOP_EDGE_HIT_THRESHOLD;
            vy = Math.abs(vy);
            y = (int) real_y;
        }
    }

    void checkCollisionBottomEdge() {
        if (real_y > BALL_BOTTOM_EDGE_HIT_THRESHOLD) {
            real_y = BALL_BOTTOM_EDGE_HIT_THRESHOLD;
            vy = -Math.abs(vy);
            y = (int) real_y;
        }
    }

    boolean checkCollisionLeftPaddle() {
        if (active && x < BALL_PADDLE_A_HIT_THRESHOLD) {

            active = false;
            return true;
        }
        return false;
    }

    boolean checkCollisionRightPaddle() {
        if (active && x > BALL_PADDLE_B_HIT_THRESHOLD) {

            active = false;
            return true;
        }
        return false;
    }
}
