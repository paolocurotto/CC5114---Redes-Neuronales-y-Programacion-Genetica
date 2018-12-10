package neuroevolution.pong;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {

    private Paddle paddle_a;
    private Paddle paddle_b;

    TAdapter(Paddle p, Paddle b) {
        this.paddle_a = p;
        this.paddle_b = b;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_UP && paddle_a.y_direction == -1 ) || key == KeyEvent.VK_DOWN && paddle_b.y_direction == 1) {
            paddle_a.y_direction = 0;
            paddle_b.y_direction = 0;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            paddle_a.y_direction = -1;
            paddle_b.y_direction = -1;
        }
        if (key == KeyEvent.VK_DOWN) {
            paddle_a.y_direction = 1;
            paddle_b.y_direction = 1;
        }
    }

}
