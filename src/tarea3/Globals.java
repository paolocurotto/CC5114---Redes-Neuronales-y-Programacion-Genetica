package tarea3;

import java.util.HashMap;
import java.util.Map;

public class Globals {
    // Game
    static final int PERIOD = 5;
    static final int DELAY = 500;
    static final int PLAYTIME = 20;
    static final int INDIVIDUAL_LIVES = 7 ;

    // Genetic algorithm
    static final int POPULATION_SIZE = 500;
    static final int K_FACTOR = POPULATION_SIZE;

    // Window
    static final int WINDOW_WIDTH = 800;
    static final int WINDOW_HEIGHT = 600;

    // Paddles
    static final int PADDLE_WIDTH = 16;
    static final int PADDLE_HEIGHT = 60;
    static final double PADDLE_SPEED = 3;
    static final int PADDLE_DISTANCE_FROM_EDGE = 7; // % of window width
    static final int PADDLE_A_INITIAL_X_POS = (int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    static final int PADDLE_B_INITIAL_X_POS = (int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2;
    static final int PADDLE_INITIAL_Y_POS = (WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2);

    // Ball
    static final double BALL_SPEED = 6;
    static final double BALL_MIN_ANGLE = 0.5;
    static final double BALL_MAX_ANGLE = 1.4;
    static final int BALL_RADIUS = 10;
    static final int BALL_INITIAL_X_POS = WINDOW_WIDTH / 2;
    static final int BALL_INITIAL_Y_POS = WINDOW_HEIGHT / 2;
    static final int BALL_PADDLE_A_HIT_THRESHOLD = PADDLE_A_INITIAL_X_POS + PADDLE_WIDTH + BALL_RADIUS;
    static final int BALL_PADDLE_B_HIT_THRESHOLD = PADDLE_B_INITIAL_X_POS - BALL_RADIUS;
    static final int BALL_TOP_EDGE_HIT_THRESHOLD = BALL_RADIUS;
    static final int BALL_BOTTOM_EDGE_HIT_THRESHOLD = WINDOW_HEIGHT - BALL_RADIUS;
    static final int BALL_LEFT_EDGE_HIT_THRESHOLD = BALL_RADIUS;
    static final int BALL_RIGHT_EDGE_HIT_THRESHOLD = WINDOW_WIDTH - BALL_RADIUS;

    // Paddle movement
    static final int PADDLE_UP = -1;
    static final int PADDLE_IDLE = 0;
    static final int PADDLE_DOWN = 1;
    static final Map<Integer,Integer> PLAYER_ACTION = new HashMap<Integer,Integer>(){{
        put(0, PADDLE_UP);
        put(1, PADDLE_IDLE);
        put(2, PADDLE_DOWN);
    }};
}