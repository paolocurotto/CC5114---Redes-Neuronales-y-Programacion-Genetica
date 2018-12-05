package neuroevolution.pong;

public interface Options {

    // Window
    int WINDOW_WIDTH = 700;
    int WINDOW_HEIGHT = 500;

    // Paddles
    int PADDLE_WIDTH = 20;
    int PADDLE_HEIGHT = 50;
    double PADDLE_SPEED = 2;
    int PADDLE_DISTANCE_FROM_EDGE = 10; // %

    // Ball
    double BALL_SPEED = 2;
    int BALL_RADIUS = 10;
}
