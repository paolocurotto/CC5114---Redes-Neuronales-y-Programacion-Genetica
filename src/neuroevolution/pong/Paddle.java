package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle implements Options{
    private int x;
    double real_x;
    private int y;
    double real_y;
    private int height;
    private int width;
    private double speed;
    int y_direction;

    Paddle(int init_x) {
        height = PADDLE_HEIGHT;
        width = PADDLE_WIDTH;
        x = init_x;
        real_x = x;
        y = (WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
        real_y = y;
        speed = PADDLE_SPEED;
        y_direction = 0;
    }


    void draw(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(this.x, this.y, this.width, this.height);
        g2d.setColor(c1);
    }


    void move() {
        real_y = real_y + speed * y_direction;
        real_y = Math.max(real_y, 0);
        real_y = Math.min(real_y, WINDOW_HEIGHT - PADDLE_HEIGHT);
        y = (int) real_y;
    }

}