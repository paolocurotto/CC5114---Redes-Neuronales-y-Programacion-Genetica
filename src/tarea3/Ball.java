package tarea3;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static tarea3.Globals.*;

public class Ball {
    Color color = Color.WHITE;
    int x;
    int y;
    double real_x;
    double real_y;
    double vx = BALL_SPEED * Math.cos(1);
    double vy = BALL_SPEED * Math.sin(1);
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

    boolean checkPositionToGetHitLeft() {
        return active && (x < BALL_PADDLE_A_HIT_THRESHOLD);
    }

    boolean checkPositionToGetHitRight() {
        return active && (x > BALL_PADDLE_B_HIT_THRESHOLD);
    }

    boolean checkCollisionLeftEdge() {
        return !active && (x < BALL_LEFT_EDGE_HIT_THRESHOLD);
    }

    boolean checkCollisionRightEdge() {
        return !active && (x > BALL_RIGHT_EDGE_HIT_THRESHOLD);
    }

    void resetBall() {
        real_x = BALL_INITIAL_X_POS;
        real_y = BALL_INITIAL_Y_POS;
        double ang = ThreadLocalRandom.current().nextDouble(BALL_MIN_ANGLE, BALL_MAX_ANGLE);
        vx = BALL_SPEED * Math.cos(ang);
        vy = BALL_SPEED * Math.sin(ang);
        if (Math.random() < 0.5) {
            vx = -vx;
        }
        if (Math.random() < 0.5) {
            vy = -vy;
        }
    }

}
