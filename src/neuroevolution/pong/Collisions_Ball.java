package neuroevolution.pong;

public class Collisions_Ball implements Options {

    Paddle paddle_a;
    Paddle paddle_b;

    /**  Collision with edges **/
    void checkCollisionsEdges(Ball ball) {
        //Hit top edge
        if (ball.real_y - BALL_RADIUS < 0) {
            ball.real_y = BALL_RADIUS;
            ball.vy *= -1;
        }
        // Hit bottom edge
        if (ball.real_y + BALL_RADIUS > WINDOW_HEIGHT) {
            ball.real_y = WINDOW_HEIGHT - BALL_RADIUS;
            ball.vy *= -1;
        }
        // Hit left edge
        if (ball.real_x - BALL_RADIUS < 0) {
            ball.real_x = BALL_RADIUS;
            ball.vx *= -1;
        }
        // Hit right edge
        if (ball.real_x + BALL_RADIUS > WINDOW_WIDTH) {
            ball.real_x = WINDOW_WIDTH - BALL_RADIUS;
            ball.vx *= -1;
        }
    }

    /**  Collision with paddles **/
    void checkCollisionPaddles(Ball ball) {
        paddle_a.checkCollisionBall(ball);
        paddle_b.checkCollisionBall(ball);
    }

    void setCollisions(Paddle p_a, Paddle p_b) {
        paddle_a = p_a;
        paddle_b = p_b;
    }
}
