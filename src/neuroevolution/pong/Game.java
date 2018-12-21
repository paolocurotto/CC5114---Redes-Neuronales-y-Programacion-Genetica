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
    private int DELAY = 500;
    private int PERIOD = 5;
    private Paddle paddle_A;
    private Paddle paddle_B;
    public List<Paddles> paddles = new ArrayList<>();

    private Ball ball;
    int score_A = 0;
    int score_B = 0;
    int playballs = PLAYBALLS;
    int playtime = PLAYTIME;
    int i = 0;

    Game() {
        for (int i = 0; i < ga.pop_size; i++) {
            Paddles ps = new Paddles(new Paddle(PADDLE_A_X_POS), new Paddle(PADDLE_B_X_POS));
            Color randomColor = new Color((int) (i * 5.1), 0 , 50);
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

        for (int i = 0; i < paddles.size(); i++) {
            if (paddles.get(i).dead)
                continue;
            // [paddle y, ball x, ball y, ball v_x, ball v_y]
            int mov = ga.population.get(i).movePaddle(new double[]{
                    (double) paddles.get(i).A.getCenterY(),
                    ball.real_x,
                    ball.real_y,
                    ball.vx,
                    ball.vy,
            });
            paddles.get(i).A.y_direction = PLAYER_ACTION.get(mov);
            paddles.get(i).B.y_direction = PLAYER_ACTION.get(mov);
        }

        checkScore();
        repaint();
    }

    private void moveObjects() {
        ball.move();
        for (Paddles paddles : paddles) {
            if (paddles.dead)
                continue;
            paddles.A.move();
            paddles.B.move();
        }
    }

    private void checkCollisions() {
        boolean thereWasCollision = false;
        int nhits = 0;

        for (Paddles pads : paddles) {
            if (pads.dead)
                continue;
            if (ball.checkCollisions(pads.A, pads.B)) {
                thereWasCollision = true;
                pads.col = true;
            }
        }
        if (thereWasCollision) {
            playtime--;
            for (Paddles pads : paddles) {
                if (pads.dead)
                    continue;
                if ((pads.A.getCenterY() + PADDLE_HEIGHT/2 + BALL_RADIUS > ball.real_y) &&
                        (pads.A.getCenterY() - PADDLE_HEIGHT/2 - BALL_RADIUS < ball.real_y)) {
                    nhits++;
                    pads.hits++;
                    continue;
                }
                if (pads.lives > 0) {
                    pads.lives--;
                } else {
                    pads.dead = true;
                }
            }

            if (playtime == 0) {
                for (int i = 0; i < paddles.size(); i++) {
                    ga.population.get(i).calculateFitness(paddles.get(i).hits);
                }
                ga.select();
                for (Paddles paddles : paddles) {
                    paddles.reset_paddles();
                }
                playballs = PLAYBALLS;
                playtime = PLAYTIME;
                ball.resetBall();
            }

        }

    }

    private void drawBackground(Graphics2D g2d) {
        setBackground(new Color(20, 130, 44));
        g2d.setColor(Color.white);
        Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
        g2d.setFont(new Font("Georgia", Font.BOLD, 80));
        //g2d.drawString(score_A + "", WINDOW_WIDTH/2 - 130, 100);
        //g2d.drawString(score_B + "", WINDOW_WIDTH/2 + 80, 100);
        g2d.drawString(playballs + "", WINDOW_WIDTH/2 -20, 100);
        g2d.setFont(new Font("Georgia", Font.BOLD, 30));
        g2d.drawString(playtime + "", WINDOW_WIDTH/2 -100, 90);
        g2d.setComposite(c);
    }

    private void drawObjects(Graphics2D g2d) {
        ball.draw(g2d);
        for (Paddles paddles : paddles) {
            if (paddles.dead)
                continue;
            paddles.A.draw(g2d);
            paddles.B.draw(g2d);
        }
    }

    private void checkScore() {
        // Point for B
        /*if (ball.real_x < 0) {
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
        */
        if (ball.real_x < 0 || WINDOW_WIDTH < ball.real_x) {
            for (Paddles pads : paddles) {
                if (pads.dead) {
                    continue;
                }
                pads.lives--;
            }
            playballs--;
            ball.resetBall();
        }

        if (playballs == 0) {
            for (int i = 0; i < paddles.size(); i++) {
               ga.population.get(i).calculateFitness(paddles.get(i).hits);
            }
            ga.select();
            for (Paddles paddles : paddles) {
                paddles.reset_paddles();
            }
            playballs = PLAYBALLS;
            playtime = PLAYTIME;
        }
    }

    class Paddles {
        public boolean col;
        int lives = 10;
        int hits = 0;
        boolean dead = false;
        Paddle A;
        Paddle B;
        Paddles(Paddle paddle, Paddle paddle1) {
            A = paddle; B = paddle1;
        }
        public void reset_paddles() {
            dead = false;
            hits = 0;
            lives = 10;
            A.hits = 0;
            B.hits = 0;
            A.real_y = PADDLE_Y_POS;
            B.real_y = PADDLE_Y_POS;
        }
    }

}
