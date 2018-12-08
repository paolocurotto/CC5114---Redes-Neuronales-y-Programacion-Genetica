package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle implements Options{
    private int x;
    private int y;
    private double real_y;
    int y_direction = 0;
    private Collisions_Paddle collisionsPaddle;

    Paddle(int init_x) {
        x = init_x;
        y = (WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
        real_y = y;
        collisionsPaddle = new Collisions_Paddle(this);
    }

    void draw(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(this.x, this.y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g2d.setColor(c1);
    }

    void move() {
        real_y = real_y + PADDLE_SPEED * y_direction;
        real_y = Math.max(real_y, 0);
        real_y = Math.min(real_y, WINDOW_HEIGHT - PADDLE_HEIGHT);
        y = (int) real_y;
    }

    int getCenterX() {
        return x + PADDLE_WIDTH / 2;
    }

    int getCenterY() {
        return y + PADDLE_HEIGHT / 2;
    }

    void checkCollisionBall(Ball ball) {
        collisionsPaddle.checkCollision(ball);
    }
}