package bomberman.Game;

import bomberman.UI.PauseOverlay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    GameEngine gameEngine;
    public Mouse(GameEngine gameEngine){
        this.gameEngine=gameEngine;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gameEngine.menuGUI.MousePressed(e);
                break;
            case GAME:
                if (gameEngine.isPaused()){
                    gameEngine.getPauseOverlay().MousePressed(e);
                }
                else {
                    gameEngine.MousePressed(e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
