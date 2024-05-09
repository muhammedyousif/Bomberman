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
        switch (GameStates.state){
            case MENU:
                gameEngine.menuGUI.keyPressed(e);
                break;
            case GAME:
                System.out.println("jsja");
                if (gameEngine.isPaused()) {
                    gameEngine.getPauseOverlay().keyPressed(e);
                }
                else {
                    gameEngine.keyPressed(e);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.state){
            case MENU:
                gameEngine.menuGUI.keyReleased(e);
                break;
            case GAME:
                gameEngine.keyReleased(e);
                break;
        }
    }
}
