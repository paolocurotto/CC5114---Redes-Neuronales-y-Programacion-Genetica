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
        paddle_A = new Paddle();
        paddle_B = new Paddle();
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
    }

}
