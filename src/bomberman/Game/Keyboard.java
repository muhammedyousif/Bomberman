package bomberman.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    GameEngine gameEngine;
    Keyboard(GameEngine gameEngine){this.gameEngine=gameEngine;}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameEngine.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameEngine.keyReleased(e);
    }
}
