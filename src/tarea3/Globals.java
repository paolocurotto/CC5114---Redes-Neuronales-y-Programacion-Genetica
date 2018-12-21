package tarea3;

import java.util.HashMap;
import java.util.Map;

class Globals {

    static final int DELAY = 500;
    static final int PERIOD = 20;
    static final int PLAYTIME = 10;
    static final int PLAYBALLS = 5;

    static final int POPULATION_SIZE = 50;
    static final int K_FACTOR = POPULATION_SIZE;

    // Window
    static final int WINDOW_WIDTH = 720;
    static final int WINDOW_HEIGHT = 500;

    // Paddles
    static final int PADDLE_WIDTH = 16;
    static final int PADDLE_HEIGHT = 60;
    static final double PADDLE_SPEED = 4;
    static final int PADDLE_DISTANCE_FROM_EDGE = 7; // % of window width
    static final int PADDLE_A_INITIAL_X_POS = (int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    static final int PADDLE_B_INITIAL_X_POS = (int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    static final int PADDLE_INITIAL_Y_POS = (WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2);


    static final int PADDLE_UP = -1;
    static final int PADDLE_IDLE = 0;
    static final int PADDLE_DOWN = 1;
    static final Map<Integer,Integer> PLAYER_ACTION = new HashMap<Integer,Integer>(){{
        put(0, PADDLE_UP);
        put(1, PADDLE_IDLE);
        put(2, PADDLE_DOWN);
    }};

    // Ball
    static final double BALL_SPEED = 5;
    static final int BALL_RADIUS = 10;
    static final int BALL_INITIAL_X_POS = WINDOW_WIDTH / 2;
    static final int BALL_INITIAL_Y_POS = WINDOW_HEIGHT / 2;
}