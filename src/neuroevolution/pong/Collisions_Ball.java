package neuroevolution.pong;

public class Collisions_Ball implements Options {

    private Ball ball;

    Collisions_Ball(Ball ball) {
        this.ball = ball;
    }

    /**  Collision with edges **/
    void checkCollisionsEdges() {
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
        /*if (ball.real_x - BALL_RADIUS < 0) {
            ball.real_x = BALL_RADIUS;
            ball.vx *= -1;
        }*/
        // Hit right edge
        /*if (ball.real_x + BALL_RADIUS > WINDOW_WIDTH) {
            ball.real_x = WINDOW_WIDTH - BALL_RADIUS;
            ball.vx *= -1;
        }*/
    }

    /**  Collision with paddles **/
    boolean checkCollisionPaddle(Paddle p) {
        return p.checkCollisionBall(ball);
    }
}
