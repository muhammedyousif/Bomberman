package bomberman.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Keyboard implements KeyListener {
    GameEngine gameEngine;
    Keyboard(GameEngine gameEngine){this.gameEngine=gameEngine;}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                try {
                    gameEngine.menuGUI.keyPressed(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case GAME:
                if (gameEngine.isPaused()) {
                    gameEngine.getPauseOverlay().keyPressed(e);
                }
                else {
                    try {
                        gameEngine.keyPressed(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                gameEngine.menuGUI.keyReleased(e);
                break;
            case GAME:
                gameEngine.keyReleased(e);
                break;
            default:
                break;
        }
    }
}
