package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle implements Options{
    public int x;
    public double real_x;
    public int y;
    public double real_y;
    public int height;
    public int width;
    public double speed;
    public int y_direction;

    public Paddle(int init_x) {
        height = PADDLE_HEIGHT;
        width = PADDLE_WIDTH;
        x = init_x;
        real_x = x;
        y = WINDOW_HEIGHT / 2;
        real_y = y;
        speed = PADDLE_SPEED;
        y_direction = 0;
    }


    public void draw(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(this.x, this.y, this.width, this.height);
        g2d.setColor(c1);
    }


    public void move() {
        real_y = real_y + speed * y_direction;
        real_y = Math.max(real_y, PADDLE_HEIGHT / 2);
        real_y = Math.min(real_y, WINDOW_HEIGHT - (PADDLE_HEIGHT / 2) - 39);
        y = (int) real_y;
    }

}