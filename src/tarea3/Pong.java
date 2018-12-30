package tarea3;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static tarea3.Globals.*;

public class Pong extends JPanel {

    State state;
    Engine engine;

    Pong() {
        state = new State();
        engine = new Engine();
        engine.state = state;
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
        drawPaddles(g2d);
        drawBall(g2d);
        Toolkit.getDefaultToolkit().sync();
    }

    private synchronized void tick() {
        repaint();
        engine.performActions();
    }

    private void drawBackground(Graphics2D g2d) {
        setBackground(new Color(20, 130, 44));
        g2d.setColor(Color.white);
        Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
        g2d.setFont(new Font("Georgia", Font.BOLD, 80));
        g2d.drawString(state.playballs + "", WINDOW_WIDTH/2 -20, 100);
        g2d.setFont(new Font("Georgia", Font.BOLD, 30));
        g2d.drawString(state.playtime + "", WINDOW_WIDTH/2 -100, 90);
        g2d.setComposite(c);
    }

    private synchronized void drawPaddles(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        for (Individual individual : state.current_individuals) {
            g2d.setColor(individual.color);
            g2d.fillRect(individual.x1, individual.y, PADDLE_WIDTH, PADDLE_HEIGHT);
            g2d.fillRect(individual.x2, individual.y, PADDLE_WIDTH, PADDLE_HEIGHT);

            g2d.setColor(Color.BLUE);
            int a = individual.n_sign_mutations / PADDLE_WIDTH;
            g2d.fillRect(individual.x1, individual.y , PADDLE_WIDTH, a);
            g2d.fillRect(individual.x2, individual.y , PADDLE_WIDTH, a);
            g2d.fillRect(individual.x1, individual.y + a + 1 , individual.n_sign_mutations % PADDLE_WIDTH, 1);
            g2d.fillRect(individual.x2, individual.y + a + 1 , individual.n_sign_mutations % PADDLE_WIDTH, 1);

            g2d.setColor(Color.RED);
            a = individual.n_amp_mutations / PADDLE_WIDTH;
            g2d.fillRect(individual.x1, individual.y + PADDLE_HEIGHT - a, PADDLE_WIDTH, a);
            g2d.fillRect(individual.x2, individual.y + PADDLE_HEIGHT - a, PADDLE_WIDTH, a);
            g2d.fillRect(individual.x1, individual.y + PADDLE_HEIGHT - a - 1, individual.n_amp_mutations % PADDLE_WIDTH, 1);
            g2d.fillRect(individual.x2, individual.y + PADDLE_HEIGHT - a - 1, individual.n_amp_mutations % PADDLE_WIDTH, 1);

        }
        g2d.setColor(c1);
    }

    private void drawBall(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        Ball ball = state.ball;
        g2d.setColor(ball.color);
        int ball_x = ball.x - BALL_RADIUS;
        int ball_y = ball.y - BALL_RADIUS;
        int ball_d = 2 * BALL_RADIUS;
        g2d.fillOval(ball_x, ball_y, ball_d, ball_d);
        g2d.setColor(c1);
    }


}
