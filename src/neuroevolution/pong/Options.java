package neuroevolution.pong;

import java.util.HashMap;
import java.util.Map;

public interface Options {

    int PLAYTIME = 10;
    int PLAYBALLS = 5;

    // Window
    int WINDOW_WIDTH = 720;
    int WINDOW_HEIGHT = 500;

    // Paddles
    int PADDLE_WIDTH = 16;
    int PADDLE_HEIGHT = 60;
    double PADDLE_SPEED = 4;
    int PADDLE_DISTANCE_FROM_EDGE = 7; // % of window width
    int PADDLE_A_X_POS = (int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    int PADDLE_B_X_POS = (int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    int PADDLE_Y_POS = (WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2);


    int PADDLE_UP = -1;
    int PADDLE_IDLE = 0;
    int PADDLE_DOWN = 1;
    Map<Integer,Integer> PLAYER_ACTION = new HashMap<Integer,Integer>(){{
        put(0, PADDLE_UP);
        put(1, PADDLE_IDLE);
        put(2, PADDLE_DOWN);
    }};


    // Ball
    double BALL_SPEED = 5;
    int BALL_RADIUS = 10;
}
