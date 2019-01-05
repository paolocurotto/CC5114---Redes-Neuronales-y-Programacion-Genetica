package tarea3;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static tarea3.Globals.*;

public class Pong extends JPanel {

    private State state;

    Pong() {
        state = new State();
        setFocusable(true);
        setDoubleBuffered(true);
        new Timer().scheduleAtFixedRate(new TimerTask() {public void run() { tick(); }}, START_DELAY, PERIOD);
    }

    private synchronized void tick() {
        repaint();
        state.performActions();
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

    private void drawBackground(Graphics2D g2d) {
        //setBackground(new Color(20, 130, 45));
        setBackground(new Color(20, 20, 20));
        g2d.setColor(Color.white);
        Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2d.setFont(new Font("Georgia", Font.BOLD, 80));
        g2d.drawString(state.playtime + "", WINDOW_WIDTH/2 -20, 100);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2d.setFont(new Font("Georgia", Font.BOLD, 20));
        g2d.drawString("Generation: " + state.generation, (int) (WINDOW_WIDTH/2 -30), 30);
        g2d.setComposite(c);
    }

    private synchronized void drawPaddles(Graphics2D g2d) {
        Color c1 = g2d.getColor();
        for (Individual individual : state.current_individuals) {
            // Body
            g2d.setColor(individual.color);
            g2d.fillRect(individual.x1, individual.y, PADDLE_WIDTH, PADDLE_HEIGHT);
            g2d.fillRect(individual.x2, individual.y, PADDLE_WIDTH, PADDLE_HEIGHT);
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
