package neuroevolution.pong;

import java.awt.*;
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
    int score_A = 0;
    int score_B = 0;

    Game() {
        paddle_A = new Paddle((int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        paddle_B = new Paddle((int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2);
        ball = new Ball();
        TAdapter ta = new TAdapter(paddle_A);
        addKeyListener(ta);
        setFocusable(true);
        setDoubleBuffered(true);
        new Timer().scheduleAtFixedRate(new TimerTask() {public void run() { tick(); }}, DELAY, PERIOD);
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

    /* Actions every tick */
    private void tick() {
        moveObjects();
        checkCollisions();
        checkScore();
        repaint();
    }

    private void moveObjects() {
        ball.move();
        paddle_A.move();
        paddle_B.move();
    }

    private void checkCollisions() {
         ball.checkCollisions(paddle_A, paddle_B);
    }

    private void drawBackground(Graphics2D g2d) {
        setBackground(new Color(20, 130, 44));
        g2d.setColor(Color.white);
        Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
        g2d.setFont(new Font("Georgia", Font.BOLD, 80));
        g2d.drawString(score_A + "", WINDOW_WIDTH/2 - 130, 100);
        g2d.drawString(score_B + "", WINDOW_WIDTH/2 + 80, 100);
        g2d.setComposite(c);
    }

    private void drawObjects(Graphics2D g2d) {
        ball.draw(g2d);
        paddle_A.draw(g2d);
        paddle_B.draw(g2d);
    }

    private void checkScore() {
        // Point for B
        if (ball.real_x < 0) {
            score_A++;
            ball.resetBall();
        }
        // Point for A
        if (ball.real_x > WINDOW_WIDTH) {
            score_B++;
            ball.resetBall();
        }
    }

}
