package neuroevolution.pong;

public class Collisions_Paddle implements Options {

    private Paddle paddle;

    Collisions_Paddle(Paddle p) {
        paddle = p;
    }

    /** Paddle collision with ball **/
    void checkCollision(Ball ball) {

        // Collision
        int y_threshold = PADDLE_HEIGHT/2 + BALL_RADIUS;
        int x_threshold = PADDLE_WIDTH/2 + BALL_RADIUS;
        double actual_y = Math.abs(paddle.getCenterY() - ball.real_y);
        double actual_x = Math.abs(paddle.getCenterX() - ball.real_x);
        if (actual_x <= x_threshold && actual_y <= y_threshold) {

            //paddle right/left edge
            double H_ratio = actual_x / x_threshold;
            double V_ratio = actual_y / y_threshold;

            // Horizontal collision
            if (H_ratio > V_ratio) {
                // from right
                if (paddle.getCenterX() < ball.real_x) {
                    ball.real_x = paddle.getCenterX() + PADDLE_WIDTH/2 + BALL_RADIUS + 1;
                    ball.vx = -ball.vx;
                }
                // from left
                else if (paddle.getCenterX() > ball.real_x) {
                    ball.real_x = paddle.getCenterX() - PADDLE_WIDTH/2 - BALL_RADIUS - 1;
                    ball.vx = -ball.vx;
                }
            }

            // Vertical collision
            else if (H_ratio <= V_ratio) {
                double y_impulse = 0.2;

                // from bottom
                if (paddle.getCenterY() < ball.real_y) {
                    ball.real_y = paddle.getCenterY() + PADDLE_HEIGHT/2 + BALL_RADIUS + 2;
                    ball.vy = Math.abs(ball.vy);
                    if (paddle.y_direction == 1) {
                        ball.vy = ball.vy + y_impulse;
                        ball.vx = (ball.vx > 0) ?
                                Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(ball.vy, 2))
                                : -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(ball.vy, 2));
                    }
                }

                // from top
                if (paddle.getCenterY() > ball.real_y) {
                    ball.real_y = paddle.getCenterY() - PADDLE_HEIGHT/2 - BALL_RADIUS - 2;
                    ball.vy = -Math.abs(ball.vy);
                    if (paddle.y_direction == -1) {
                        ball.vy = ball.vy - y_impulse;
                        ball.vx = (ball.vx > 0) ?
                                Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(ball.vy, 2))
                                : -Math.sqrt(Math.pow(BALL_SPEED, 2) - Math.pow(ball.vy, 2));
                    }
                }
            }
        }
    }
}
