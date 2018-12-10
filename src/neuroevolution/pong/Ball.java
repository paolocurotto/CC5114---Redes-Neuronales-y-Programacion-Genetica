package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

public class Ball implements Options {

    private int x;
    private int y;
    double real_x;
    double real_y;
    double angle_p = 0;
    double vx = BALL_SPEED * Math.cos(angle_p);
    double vy = BALL_SPEED * Math.sin(angle_p);
    Collisions_Ball collisionsBall = new Collisions_Ball(this);

    Ball() {
        x = WINDOW_WIDTH / 2;
        y = WINDOW_HEIGHT / 2;
        real_x = x;
        real_y = y;
    }

    void draw(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x - BALL_RADIUS, y - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
        g2d.setColor(c1);
    }

    void move() {
        real_x = real_x + vx;
        real_y = real_y + vy;
        x = (int) real_x;
        y = (int) real_y;
    }

    void checkCollisions(Paddle a, Paddle b) {
        collisionsBall.checkCollisionsEdges();
        collisionsBall.checkCollisionPaddle(a);
        collisionsBall.checkCollisionPaddle(b);
    }

    public void resetBall() {
        real_x = WINDOW_WIDTH / 2;
        real_y = WINDOW_HEIGHT / 2;
        double ang = ThreadLocalRandom.current().nextDouble(0.350,1.047);
        double r = Math.random();
        if (r < 0.25) {
        } else if (r < 0.5) {
            ang = ang + Math.PI/2;
        } else if (r < 0.75) {
            ang = ang + Math.PI;
        } else {
            ang = ang + (3/4.0)*Math.PI;
        }
        vx = BALL_SPEED * Math.cos(ang);
        vy = BALL_SPEED * Math.sin(ang);
    }
}
