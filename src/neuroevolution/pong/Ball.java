package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Ball implements Options {

    public int x;
    public int y;
    public int r;
    public int d;
    public double real_x;
    public double real_y;
    private double speed;
    public int angle;

    public Ball() {
        r = BALL_RADIUS;
        d = r * 2;
        x = WINDOW_WIDTH / 2;
        y = WINDOW_HEIGHT / 2;
        real_x = x;
        real_y = y;
        angle = 170;
        speed = BALL_SPEED;
    }


    public void draw(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x - r, y - r, d, d);
        g2d.setColor(c1);
    }

    public void move() {
        // Update position
        real_x = real_x + speed * Math.cos(Math.toRadians(angle));
        real_y = real_y + speed * Math.sin(Math.toRadians(angle));
        // Update position to be drawn
        x = (int) real_x;
        y = (int) real_y;
    }

}
