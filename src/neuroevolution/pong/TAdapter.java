package neuroevolution.pong;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {

    private Paddle paddle;

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_UP && paddle.y_direction == -1 ) || key == KeyEvent.VK_DOWN && paddle.y_direction == 1) {
            paddle.y_direction = 0;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            paddle.y_direction = -1;
        }
        if (key == KeyEvent.VK_DOWN) {
            paddle.y_direction = 1;
        }
    }

    void setPaddle(Paddle p) {
        paddle = p;
    }
}
