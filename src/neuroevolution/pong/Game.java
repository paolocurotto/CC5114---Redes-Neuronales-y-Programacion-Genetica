package neuroevolution.pong;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Game extends JPanel implements Options {

    private static final long serialVersionUID = 1L;
    private int DELAY = 50;
    private int PERIOD = 10;
    private Paddle paddle_A;
    private Paddle paddle_B;
    private Ball ball;

    Game() {
        paddle_A = new Paddle((int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        paddle_B = new Paddle((int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        ball = new Ball();
        ball.collisionsBall.setCollisions(paddle_A, paddle_B);
        TAdapter ta = new TAdapter();
        ta.setPaddle(paddle_A);
        addKeyListener(ta);
        setFocusable(true);
        setDoubleBuffered(true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        drawBackground(g2d);
        drawObjects(g2d);
        Toolkit.getDefaultToolkit().sync();
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            moveObjects();
            checkCollisions();
            repaint();
        }
    }

    private void moveObjects() {
        ball.move();
        paddle_A.move();
        paddle_B.move();
    }

    private void checkCollisions() {
         ball.checkCollisions();
    }

    private void drawBackground(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        g2d.setColor(new Color(20, 130, 44));
        g2d.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g2d.setColor(c1);

        g2d.setColor(Color.red);
        //g2d.setFont(monoFont);
        FontMetrics fm = g2d.getFontMetrics();
        double w = fm.stringWidth("Java Source");
        double h = fm.getAscent();
        //g2d.drawString("Java Source", 120 - (w / 2), 120 + (h / 4));
        Observer a = new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };

        Observable b = new Observable();



    }

    private void drawObjects(Graphics2D g2d) {
        ball.draw(g2d);
        paddle_A.draw(g2d);
        paddle_B.draw(g2d);
    }
}
