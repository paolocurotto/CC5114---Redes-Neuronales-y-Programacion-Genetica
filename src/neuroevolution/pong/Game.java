package neuroevolution.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Game extends JPanel implements Options {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private int DELAY = 10;
    private int PERIOD = 10;
    private Paddle paddle_A;
    private Paddle paddle_B;
    private Ball ball;

    public Game() {
        paddle_A = new Paddle((int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        paddle_B = new Paddle((int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        ball = new Ball();
        TAdapter ta = new TAdapter();
        ta.setPaddle(paddle_A);
        addKeyListener(ta);
        setFocusable(true);
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawPitch(g2d);
        drawObjects(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawPitch(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(new Color(20, 130, 44));
        g2d.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g2d.setColor(c1);
    }

    private void drawObjects(Graphics2D g2d) {
        paddle_A.draw(g2d);
        paddle_B.draw(g2d);
        ball.draw(g2d);
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            ball.move();
            paddle_A.move();
            paddle_B.move();
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
         /**
         /*  Ball collision
         **/

        //Hit top edge
        if (ball.real_y - ball.r < 0) {
            ball.real_y = ball.r;
            ball.angle = 360 - ball.angle;
        }
        // Hit bottom edge
        if (ball.real_y + ball.r > WINDOW_HEIGHT) {
            ball.real_y = WINDOW_HEIGHT - ball.r;
            ball.angle = 360 - ball.angle;
        }
        // Hit left edge
        if (ball.real_x - ball.r < 0) {
            ball.real_x = ball.r;
            ball.angle = 180 - ball.angle;
        }
        // Hit right edge
        if (ball.real_x + ball.r > WINDOW_WIDTH) {
            ball.real_x = WINDOW_WIDTH - ball.r;
            ball.angle = (180 - ball.angle) % 360;
        }

        // Collision ball-paddle
        //if (ball.real_x - ball.r < paddle_A.real_x + paddle_A.PADDLE_WIDTH &&
        //        Math.abs(paddle_A.real_y + PADDLE_HEIGHT/2 - ball.y) < PADDLE_HEIGHT / 2) {
        //    ball.angle = 180 - ball.angle;
        //}









    }

}
