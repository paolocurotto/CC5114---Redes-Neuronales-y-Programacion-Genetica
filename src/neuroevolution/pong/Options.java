package neuroevolution.pong;

public interface Options {

    // Window
    int WINDOW_WIDTH = 700;
    int WINDOW_HEIGHT = 500;

    // Paddles
    int PADDLE_WIDTH = 16;
    int PADDLE_HEIGHT = 60;
    double PADDLE_SPEED = 4;
    int PADDLE_DISTANCE_FROM_EDGE = 10; // % of window width

    // Ball
    double BALL_SPEED = 5;
    int BALL_RADIUS = 10;
}
