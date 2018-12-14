package neuroevolution.pong;

import neuroevolution.GeneticAlgorithm;
import neuroevolution.Individual;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.JPanel;

public class Game extends JPanel implements Options {

    GeneticAlgorithm ga = new GeneticAlgorithm();

    private static final long serialVersionUID = 1L;
    private int DELAY = 1000;
    private int PERIOD = 5;
    private Paddle paddle_A;
    private Paddle paddle_B;
    public List<Paddles> paddles = new ArrayList<>();

    private Ball ball;
    int score_A = 0;
    int score_B = 0;
    int playballs = 0;
    int i = 0;

    Game() {
        for (int i = 0; i < ga.pop_size; i++) {
            Paddles ps =
                    new Paddles(new Paddle((int) (WINDOW_WIDTH * (PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2),
                    new Paddle((int) (WINDOW_WIDTH * (1 - PADDLE_DISTANCE_FROM_EDGE / 100.0)) - PADDLE_WIDTH / 2));
            Color randomColor = new Color((int)(Math.random() * 0x1000000));
            ps.A.color = randomColor;
            ps.B.color = randomColor;
            paddles.add(ps);
        }
        ball = new Ball();
        TAdapter ta = new TAdapter(paddle_A, paddle_B);
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

        for (int i = 0; i < paddles.size(); i++) {
            // [paddle y, ball x, ball y, ball v_x, ball v_y]
            int mov = ga.population.get(i).movePaddle(new double[]{
                    (double) paddles.get(i).A.getCenterY(),
                    ball.real_x,
                    ball.real_y,
                    ball.vx,
                    ball.vy,
            });
            paddles.get(i).A.y_direction = mov;
            paddles.get(i).B.y_direction = mov;
        }

    }

    private void moveObjects() {
        ball.move();
        for (Paddles paddles : paddles) {
            paddles.A.move();
            paddles.B.move();
        }
    }

    private void checkCollisions() {
        for (Paddles paddles : paddles) {
            ball.checkCollisions(paddles.A, paddles.B);
        }
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
        for (Paddles paddles : paddles) {
            paddles.A.draw(g2d);
            paddles.B.draw(g2d);
        }
    }

    private void checkScore() {
        // Point for B
        if (ball.real_x < 0) {
            playballs++;
            score_B++;
            ball.resetBall();
        }
        // Point for A
        if (ball.real_x > WINDOW_WIDTH) {
            playballs++;
            score_A++;
            ball.resetBall();
        }

        if (playballs == 10) {
            for (int i = 0; i < paddles.size(); i++) {
               ga.population.get(i).calculateFitness(paddles.get(i).A.hits + paddles.get(i).B.hits);
            }
        }
    }

    class Paddles {
        Paddle A;
        Paddle B;

        public Paddles(Paddle paddle, Paddle paddle1) {
            A = paddle; B = paddle1;
        }
    }

}
