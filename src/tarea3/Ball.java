package tarea3;

import java.awt.*;

import static tarea3.Globals.BALL_INITIAL_X_POS;
import static tarea3.Globals.BALL_INITIAL_Y_POS;
import static tarea3.Globals.BALL_SPEED;

public class Ball {

    Color color = Color.WHITE;
    int x;
    int y;
    double real_x;
    double real_y;
    double angle_p = 2.6;
    double vx = BALL_SPEED * Math.cos(angle_p);
    double vy = BALL_SPEED * Math.sin(angle_p);

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

}
