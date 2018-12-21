package tarea3;

import java.awt.*;

import static tarea3.Globals.*;

public class Paddles {

    Color color;
    int y;
    double real_y;
    int x1;
    int x2;
    int lives;
    int y_direction = PADDLE_IDLE;

    Paddles() {
        x1 = PADDLE_A_INITIAL_X_POS;
        x2 = PADDLE_B_INITIAL_X_POS;
        y = PADDLE_INITIAL_Y_POS;
        real_y = y;
    }

    void move() {
        real_y = real_y + PADDLE_SPEED * y_direction;
        real_y = Math.max(real_y, 0);
        real_y = Math.min(real_y, WINDOW_HEIGHT - PADDLE_HEIGHT);
        y = (int) real_y;
    }


}
